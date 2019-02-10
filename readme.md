**REST API microservice. Kotlin and Spring Boot based image uploader**

## Usage

#### Run application from source:
~~~~
gradlew bootRun
~~~~

#### API
##### Upload base64 encoded images from JSON
~~~~
curl -H "Content-Type: application/json" --data "[{\"name\" : \"Viareggio\", \"data\" : \"data:image/jpeg;base64,/9j/4AAQSkZJRg...skipped...==\"}]" localhost:8080/image?preview=true
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
curl -F "files=@Viareggio.jpg" -F "files=@Orvieto.jpg" http://localhost:8080/image?preview=true
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

#### Postman project
_measure.postman_collection.json_