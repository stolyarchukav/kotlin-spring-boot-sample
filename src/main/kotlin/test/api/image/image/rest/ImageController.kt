package test.api.image.image.rest

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import test.api.image.image.storage.ImageStorage
import java.net.URL
import java.util.stream.Collectors

@RequestMapping("image")
@RestController
final class ImageController(private val storage: ImageStorage) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadMultipart(@RequestParam("files") files: List<MultipartFile>,
                        @RequestParam("preview", defaultValue = "false") preview: Boolean) =
            toResponse(storage.storeFiles(files, preview))

    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun uploadUrl(@RequestParam("url") urls: List<URL>,
                  @RequestParam("preview", defaultValue = "false") preview: Boolean) =
            toResponse(storage.storeFromUrls(urls, preview))

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun uploadEncoded(@RequestBody images: List<EncodedImage>,
                      @RequestParam("preview", defaultValue = "false") preview: Boolean) =
            toResponse(storage.storeEncodedBase64(images, preview))

    private fun toResponse(fileNames: List<Pair<String, String?>>) =
            ImageResponse(fileNames
                    .stream()
                    .map { pair -> ImageAndPreview(pair.first, pair.second) }
                    .collect(Collectors.toList()), fileNames.size)

}
