package test.api.image.image.rest

import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import test.api.image.image.logger
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.*
import javax.imageio.ImageIO

@Component
class FileImageStorage : ImageStorage {

    val log = logger()

    override fun storeFiles(files: List<MultipartFile>, preview: Boolean) {
        log.info("Store image files: {}, with preview: {}", files.size, preview)
        files.forEach { file ->
            val fileName = file.originalFilename ?: generateName()
            try {
                val storedFile = storeFile(file.inputStream, fileName)
                storePreview(preview, storedFile)
            } catch (ex: IOException) {
                throw InternalServiceException("Cannot save file $fileName")
            }
        }
    }

    override fun storeFromUrls(urls: List<URL>, preview: Boolean) {
        log.info("Store images from URLs: {}, with preview: {}", urls, preview)
        urls.forEach{url ->
            storePreview(preview, storeFile(url.openStream(), generateName()))
        }
    }

    private fun storePreview(path: Path) {
        val preview = BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)
        val image = ImageIO.read(path.toFile())
        preview.createGraphics().drawImage(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null)
        val name = FilenameUtils.removeExtension(path.fileName.toString())
        ImageIO.write(preview, "jpg", File("preview_$name.jpg"))
    }

    private fun storePreview(preview: Boolean, storedFile: Path) {
        if (preview) {
            storePreview(storedFile)
        }
    }

    private fun storeFile(stream: InputStream, fileName: String): Path {
        val path = Path.of(fileName)
        Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING)
        return path
    }

    private fun generateName(): String {
        val timestamp = System.currentTimeMillis()
        return "image_$timestamp.jpg"
    }

    override fun storeEncodedBase64(images: List<EncodedImage>, preview: Boolean) {
        log.info("Store encoded base 64 images: {}, with preview: {}", images, preview)
        images.forEach{image ->
            val storedFile = storeEncoded(image)
            storePreview(preview, storedFile)
        }
    }

    private fun storeEncoded(image: EncodedImage): Path {
        val base64 = getBase64(image)
        val inputStream = ByteArrayInputStream(Base64.getDecoder().decode(base64))
        val file = File("${image.name}.jpg")
        ImageIO.write(ImageIO.read(inputStream), "jpg", file)
        return file.toPath()
    }

    private fun getBase64(image: EncodedImage): String {
        val data = image.data
        val parts = data.split(",")
        val format = parts[0]
        if (format != "data:image/jpeg;base64") {
            log.warn("Data format is not supported")
            throw InvalidRequestException("Unsupported data format: $format")
        }
        return parts[1]
    }

}
