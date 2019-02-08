package test.api.image.image.rest

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URL
import java.util.concurrent.atomic.AtomicLong

@RestController("image")
class ImageController(val storage : ImageStorage) {

    val counter = AtomicLong()

    @GetMapping("/test")
    fun greeting() = counter.incrementAndGet()

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadMultipart(@RequestParam("files", required = true) files: List<MultipartFile>,
                        @RequestParam("preview", defaultValue = "false") preview: Boolean) = storage.storeFiles(files, preview)

    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun uploadUrl(@RequestParam("url", required = true) url : URL,
                  @RequestParam("preview", defaultValue = "false") preview : Boolean) = storage.storeUrl(url, preview)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun uploadEncoded(@RequestBody(required = true) image : EncodedImage) = println(image)

}
