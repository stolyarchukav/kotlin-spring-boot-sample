package test.api.image.image.rest

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.net.URL

@RestController("image")
class ImageController(val storage : ImageStorage) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadMultipart(@RequestParam("files", required = true) files: List<MultipartFile>,
                        @RequestParam("preview", defaultValue = "false") preview: Boolean) = storage.storeFiles(files, preview)

    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun uploadUrl(@RequestParam("url", required = true) urls : List<URL>,
                  @RequestParam("preview", defaultValue = "false") preview : Boolean) = storage.storeUrl(urls, preview)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun uploadEncoded(@RequestBody(required = true) image : EncodedImage) = storage.storeEncoded(image)

}
