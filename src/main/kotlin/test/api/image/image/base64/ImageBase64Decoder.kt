package test.api.image.image.base64

import org.springframework.stereotype.Component
import test.api.image.image.exception.InvalidRequestException
import test.api.image.image.logger
import test.api.image.image.rest.EncodedImage
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*

@Component
final class ImageBase64Decoder : Base64Decoder {

    private val log = logger()

    override fun decode(image: EncodedImage): InputStream =
            ByteArrayInputStream(Base64.getDecoder().decode(parseBase64(image)))

    private fun parseBase64(image: EncodedImage): String {
        val data = image.data
        val parts = data.split(",")
        validateParts(parts)
        return parts[1]
    }

    private fun validateParts(parts: List<String>) {
        if (parts.size < 2) {
            log.warn("Invalid 'data' requested")
            throw InvalidRequestException("Invalid 'data' requested")
        }
        val format = parts[0]
        if (format != "data:image/jpeg;base64") {
            log.warn("Data format is not supported: {}", format)
            throw InvalidRequestException("Unsupported data format: $format")
        }
    }

}
