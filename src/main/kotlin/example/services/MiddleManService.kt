package example.services

import example.clients.MiddleManClient
import example.clients.models.Interest
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