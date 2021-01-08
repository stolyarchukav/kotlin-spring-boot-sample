package test.api.image.image.preview

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import test.api.image.image.logger
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Path
import javax.imageio.ImageIO

@Component
final class FilePreviewStorage : PreviewStorage {

    override fun storePreview(imagePath: Path): String {
        val name = FilenameUtils.removeExtension(imagePath.fileName.toString())
        val fileName = "preview_$name.jpg"
        GlobalScope.launch {
            val preview = BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)
            val image = ImageIO.read(imagePath.toFile())
            preview.createGraphics().drawImage(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null)
            ImageIO.write(preview, "jpg", File(fileName))
            logger().info("Preview is stored")
        }
        return fileName
    }

}
