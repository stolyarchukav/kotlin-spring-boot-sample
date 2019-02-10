package test.api.image.image.rest

class ImageResponse(val names: List<ImageAndPreview>, val count: Int)

class ImageAndPreview(val image: String, val preview: String?)