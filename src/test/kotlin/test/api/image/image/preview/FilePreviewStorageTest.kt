package test.api.image.image.preview

import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.file.Path

class FilePreviewStorageTest {

    private val storage: FilePreviewStorage = FilePreviewStorage()

    @Test
    fun storePreview() {
        val name = storage.storePreview(Path.of(this::class.java.getResource("/Orvieto.jpg").toURI()))
        assertEquals("preview_Orvieto.jpg", name)
    }

}
