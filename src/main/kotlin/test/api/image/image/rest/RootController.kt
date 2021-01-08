package test.api.image.image.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
final class RootController {

    @GetMapping
    fun root() = "Hello from Image Uploader!"

}
