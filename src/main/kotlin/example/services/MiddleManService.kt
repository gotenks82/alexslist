package example.services

import example.clients.MiddleManClient
import example.clients.models.Interest
import example.clients.models.TradeOpportunityStatus
import example.models.Item
import example.models.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MiddleManService @Inject constructor(
   val middleManClient: MiddleManClient
) {

    fun saveInterest(user: User, item: Item) {
        if (user.id == item.userId) return

        middleManClient.saveInterest(user.id, item.toInterest())
    }

    fun getNotifications(user: User) = middleManClient.getNotifications(user.id)

    fun getTrades(user: User) = middleManClient.getTrades(user.id)

    fun getTrade(tradeId: String) = middleManClient.getTrade(tradeId)

    fun updateStatus(user: User, tradeId: String, status: TradeOpportunityStatus) = middleManClient.updateStatus(user.id, tradeId, status)

    fun getMessages(tradeId: String) = middleManClient.getMessages(tradeId)

    fun postMessage(user: User, tradeId: String, content: String) = middleManClient.postMessage(user.id, tradeId, mapOf("content" to content))
}

private fun Item.toInterest() = Interest(
        itemId = id,
        itemUserid = userId,
        itemName = name,
        itemImageUrl = imgUrl ?: "",
        itemUrl = "http://alexslist.local:8080/ad(id=$id)",
        itemPrice = price,
        platform = "AlexsList"
)