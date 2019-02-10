package test.api.image.image.storage

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import test.api.image.image.exception.InternalServiceException
import test.api.image.image.exception.InvalidRequestException
import test.api.image.image.logger
import test.api.image.image.preview.PreviewStorage
import test.api.image.image.rest.EncodedImage
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.stream.Collectors
import javax.imageio.ImageIO

@Component
class FileImageStorage(val previewStorage: PreviewStorage) : ImageStorage {

    val log = logger()

    override fun storeFiles(files: List<MultipartFile>, preview: Boolean): List<Pair<String, String?>> {
        log.info("Store image files: {}, with preview: {}", files.size, preview)
        return files.stream().map { file ->
            val fileName = file.originalFilename ?: generateName()
            try {
                val storedFile = storeFile(file.inputStream, fileName)
                val storedPreview = storePreview(preview, storedFile)
                Pair(fileName, storedPreview)
            } catch (ex: IOException) {
                throw InternalServiceException("Cannot save file $fileName")
            }
        }.collect(Collectors.toList())
    }

    override fun storeFromUrls(urls: List<URL>, preview: Boolean): List<Pair<String, String?>> {
        log.info("Store images from URLs: {}, with preview: {}", urls, preview)
        return urls.stream().map { url ->
            val fileName = generateName()
            val storedFile = storeFile(url.openStream(), fileName)
            val storedPreview = storePreview(preview, storedFile)
            Pair(fileName, storedPreview)
        }.collect(Collectors.toList())
    }

    override fun storeEncodedBase64(images: List<EncodedImage>, preview: Boolean): List<Pair<String, String?>> {
        log.info("Store encoded base 64 images: {}, with preview: {}", images, preview)
        return images.stream().map { image ->
            val storedFile = storeEncoded(image)
            val storePreview = storePreview(preview, storedFile)
            Pair(storedFile.fileName.toString(), storePreview)
        }.collect(Collectors.toList())
    }

    private fun storePreview(preview: Boolean, storedFile: Path): String? {
        if (preview) {
            return previewStorage.storePreview(storedFile)
        }
        return null
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
