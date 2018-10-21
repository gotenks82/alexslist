package example.clients

import example.clients.models.Interest
import example.clients.models.TradeOpportunity
import example.clients.models.TradeOpportunityStatus
import example.clients.models.TradeSummary
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

    @Get("/user/{id}/trades")
    fun getTrades(id: String) : List<TradeSummary>

    @Get("/trade/{id}")
    fun getTrade(id: String) : TradeOpportunity

    @Post("/user/{id}/trades/{tradeId}/status/{status}")
    fun updateStatus(id: String, tradeId: String, status: TradeOpportunityStatus)
}