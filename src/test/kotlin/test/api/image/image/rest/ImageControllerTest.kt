package test.api.image.image.rest

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import test.api.image.image.storage.ImageStorage

@RunWith(MockitoJUnitRunner::class)
class ImageControllerTest {

    @Mock
    private lateinit var storage: ImageStorage

    @InjectMocks
    private lateinit var controller: ImageController

    @Test
    fun uploadEncoded() {
        val images = listOf(image(1), image(2))
        Mockito.`when`(storage.storeEncodedBase64(images, false)).thenReturn(listOf(pair(1), pair(2)))

        val response = controller.uploadEncoded(images, false)

        assertEquals(2, response.count)
        assertEquals("image_1.jpg", response.names[0].image)
        assertEquals("image_2.jpg", response.names[1].image)
        assertEquals("preview_1.jpg", response.names[0].preview)
        assertEquals("preview_2.jpg", response.names[1].preview)
    }

    //TODO cover other methods

    private fun pair(suffix: Int): Pair<String, String?> = Pair("image_$suffix.jpg", "preview_$suffix.jpg")

    private fun image(suffix: Int) = EncodedImage("test_image_$suffix", "test_data_$suffix")

}
