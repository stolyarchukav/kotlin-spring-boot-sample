package test.api.image.image

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutineProbe

fun main() = runBlocking {
    printThread("m")
    val job = launch {
        printThread("c")
        delay(500)
        println("World!")
    }
    coroutineScope {
        launch {
            printThread("csl")
            delay(300)
            println("Il Mondo!")
        }
        printThread("cs")
        delay(100)
        println("Ciao,")
    }
    println("Hello,")
    job.join()
    printThread("m")
}

private fun printThread(tag : String) {
    println("$tag: ${Thread.currentThread().name}")
}
