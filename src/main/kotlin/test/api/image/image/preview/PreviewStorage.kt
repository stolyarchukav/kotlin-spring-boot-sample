package test.api.image.image.preview

import java.nio.file.Path

interface PreviewStorage {

    fun storePreview(imagePath: Path): String

}
