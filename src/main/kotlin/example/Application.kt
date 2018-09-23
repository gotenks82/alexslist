package example

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("example")
                .mainClass(Application.javaClass)
                .start()
    }
}