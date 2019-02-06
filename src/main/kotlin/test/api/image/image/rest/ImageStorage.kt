package test.api.image.image.rest

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate

@Component
class ImageStorage {

    fun storeFile(file: MultipartFile): String {
        val fileName = file.originalFilename ?: "file_" + LocalDate.now()
        try {
            Files.copy(file.inputStream, Path.of(fileName))
            return fileName
        } catch (ex: IOException) {
            throw InternalServiceException("Cannot save file $fileName")
        }
    }
}
