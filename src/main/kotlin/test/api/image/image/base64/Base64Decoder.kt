package test.api.image.image.base64

import test.api.image.image.rest.EncodedImage
import java.io.InputStream

interface Base64Decoder {

    fun decode(image: EncodedImage): InputStream

}
