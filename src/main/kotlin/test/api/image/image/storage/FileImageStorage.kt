package test.api.image.image.storage

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import test.api.image.image.base64.Base64Decoder
import test.api.image.image.logger
import test.api.image.image.preview.PreviewStorage
import test.api.image.image.rest.EncodedImage
import java.io.InputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.stream.Collectors

@Component
class FileImageStorage(val previewStorage: PreviewStorage, val base64Decoder: Base64Decoder) : ImageStorage {

    private val log = logger()

    override fun storeFiles(files: List<MultipartFile>, preview: Boolean): List<Pair<String, String?>> {
        log.info("Store image files: {}, with preview: {}", files.size, preview)
        return files.stream().map { file ->
            val fileName = file.originalFilename ?: generateName()
            val storedFile = storeFile(file.inputStream, fileName)
            val storedPreview = storePreview(storedFile, preview)
            Pair(fileName, storedPreview)
        }.collect(Collectors.toList())
    }

    override fun storeFromUrls(urls: List<URL>, preview: Boolean): List<Pair<String, String?>> {
        log.info("Store images from URLs: {}, with preview: {}", urls, preview)
        return urls.stream().map { url ->
            val fileName = generateName()
            val storedFile = storeFile(url.openStream(), fileName)
            val storedPreview = storePreview(storedFile, preview)
            Pair(fileName, storedPreview)
        }.collect(Collectors.toList())
    }

    override fun storeEncodedBase64(images: List<EncodedImage>, preview: Boolean): List<Pair<String, String?>> {
        log.info("Store encoded base 64 images: {}, with preview: {}", images, preview)
        return images.stream().map { image ->
            val fileName = "${image.name}.jpg"
            val storedFile = storeFile(base64Decoder.decode(image), fileName)
            val storePreview = storePreview(storedFile, preview)
            Pair(fileName, storePreview)
        }.collect(Collectors.toList())
    }

    private fun storePreview(storedFile: Path, preview: Boolean): String? {
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

}
