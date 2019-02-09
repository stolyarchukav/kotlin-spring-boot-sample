package test.api.image.image.rest

import org.springframework.web.multipart.MultipartFile
import java.net.URL

interface ImageStorage {

    fun storeFiles(files: List<MultipartFile>, preview: Boolean)

    fun storeFromUrls(urls: List<URL>, preview: Boolean)

    fun storeEncodedBase64(images: List<EncodedImage>, preview: Boolean)

}
