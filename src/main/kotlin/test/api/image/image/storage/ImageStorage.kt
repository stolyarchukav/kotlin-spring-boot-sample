package test.api.image.image.storage

import org.springframework.web.multipart.MultipartFile
import test.api.image.image.rest.EncodedImage
import java.net.URL

interface ImageStorage {

    fun storeFiles(files: List<MultipartFile>, preview: Boolean): List<Pair<String, String?>>

    fun storeFromUrls(urls: List<URL>, preview: Boolean): List<Pair<String, String?>>

    fun storeEncodedBase64(images: List<EncodedImage>, preview: Boolean): List<Pair<String, String?>>

}
