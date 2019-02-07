package test.api.image.image.rest

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

@Component
class ImageStorage {

    fun storeFiles(files: List<MultipartFile>) {
        files.forEach { file ->
            val fileName = file.originalFilename ?: generateFileName()
            try {
                storeFile(file.inputStream, fileName)
            } catch (ex: IOException) {
                throw InternalServiceException("Cannot save file $fileName")
            }
        }
    }

    fun storeUrl(url: URL) {
        storeFile(url.openStream(), generateFileName()+ ".png")
    }

    private fun storeFile(stream: InputStream, fileName: String) {
        Files.copy(stream, Path.of(fileName), StandardCopyOption.REPLACE_EXISTING)
    }

    private fun generateFileName() = "file_" + System.currentTimeMillis()

}
