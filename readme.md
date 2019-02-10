**REST API microservice. Kotlin and Spring Boot based image uploader**

## Usage

#### Run application from source:
~~~~
gradlew bootRun
~~~~

#### API
##### Upload base64 encoded images from JSON
~~~~
curl -H "Content-Type: application/json" --data "[{\"name\" : \"Viareggio\", \"data\" : \"data:image/jpeg;base64,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\"}]" http://localhost:8080/image?preview=true
~~~~
~~~~json
{
    "names": [
        {
            "image": "Viareggio.jpg",
            "preview": "preview_Viareggio.jpg"
        }
    ],
    "count": 1
}
~~~~

##### Upload images from files
~~~~
curl -F "files=@src/test/resources/Viareggio.jpg" -F "files=@src/test/resources/Orvieto.jpg" http://localhost:8080/image?preview=true
~~~~
~~~~json
{
    "names": [
        {
            "image": "Viareggio.jpg",
            "preview": "preview_Viareggio.jpg"
        },
        {
            "image": "Orvieto.jpg",
            "preview": "preview_Orvieto.jpg"
        }        
    ],
    "count": 1
}
~~~~

##### Upload image from URLs
~~~~
curl -H "Content-Type: application/x-www-form-urlencoded" -d "url=https://www.destetravel.com/wp-content/uploads/2017/06/pistoia-600x380.jpg" http://localhost:8080/image?preview=true
~~~~
~~~~json
{
    "names": [
        {
            "image": "image_1549792171041.jpg",
            "preview": "preview_image_1549792171041.jpg"
        },
        {
            "image": "image_1549792172237.jpg",
            "preview": "preview_image_1549792172237.jpg"
        }
    ],
    "count": 2
}
~~~~

Use _preview=true_ query parameter to generate and save 100x100 image preview

#### Build (compile, unit tests, jar)
~~~~
gradlew clean build
~~~~

#### Integration tests
~~~~
gradlew integrationTest
~~~~

### Postman project
_measure.postman_collection.json_

### Continuous Integration
https://circleci.com/gh/stolyarchukav/kotlin-spring-boot-sample