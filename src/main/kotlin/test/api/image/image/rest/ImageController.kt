package test.api.image.image.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.atomic.AtomicLong

@RestController("image")
class ImageController(val storage : ImageStorage) {

    val counter = AtomicLong()

    @GetMapping("/test")
    fun greeting() = counter.incrementAndGet()

    @PostMapping
    fun upload(@RequestParam("file") file : MultipartFile) = storage.storeFile(file)

}
