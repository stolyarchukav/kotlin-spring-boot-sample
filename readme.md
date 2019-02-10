**REST API microservice. Kotlin and Spring Boot based image uploader**

##Usage

####Run application from source:
~~~~
gradlew bootRun
~~~~

####API
#####Upload base64 encoded image
use _preview=true_ query parameter to generate and save 100x100 image preview 
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

####Build (compile, unit tests, jar)
~~~~
gradlew clean build
~~~~

####Integration tests
~~~~
gradlew integrationTest
~~~~

####Postman project
_measure.postman_collection.json_