package example.clients

import example.clients.models.Interest
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.Client

@Client("middleman")
interface MiddleManClient {

    @Post("/user/{id}/interest")
    fun saveInterest(id: String, @Body interest: Interest)

    @Get("/user/{id}/notifications")
    fun getNotifications(id: String) : List<String>
}