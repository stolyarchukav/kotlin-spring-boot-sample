package test.api.image.image.rest

import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
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
class ImageStorage {

    fun storeFiles(files: List<MultipartFile>, preview: Boolean) {
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

    private fun storePreview(path: Path) {
        val preview = BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)
        val image = ImageIO.read(path.toFile())
        preview.createGraphics().drawImage(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null)
        val name = FilenameUtils.removeExtension(path.fileName.toString())
        ImageIO.write(preview, "jpg", File("preview_$name.jpg"))
    }

    fun storeUrl(urls: List<URL>, preview: Boolean) {
        urls.forEach{url ->
            storePreview(preview, storeFile(url.openStream(), generateName()))
        }
    }

    private fun storePreview(preview: Boolean, storedFile: Path) {
        if (preview) {
            storePreview(storedFile)
        }
    }

    private fun storeFile(stream: InputStream, fileName: String) : Path {
        val path = Path.of(fileName)
        Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING)
        return path
    }

    private fun generateName(): String {
        val timestamp = System.currentTimeMillis()
        return "image_$timestamp.jpg"
    }

    fun storeEncoded(image: EncodedImage) {
        val data = image.data
        val parts = data.split(",")
        val format = parts[0]
        if (format != "data:image/jpeg;base64") {
            throw InvalidRequestException("Unsupported data format: $format")
        }
        val base64 = parts[1]
        val inputStream = ByteArrayInputStream(Base64.getDecoder().decode(base64))
        ImageIO.write(ImageIO.read(inputStream), "jpg", File("${image.name}.jpg"))
    }

}
