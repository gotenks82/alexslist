package example.controllers

import example.clients.models.TradeOpportunityStatus
import example.clients.models.TradeStepWithUserName
import example.models.TradeUpdate
import example.services.ItemService
import example.services.MiddleManService
import example.services.UserService
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.Secured
import io.micronaut.views.View
import java.security.Principal

@Controller("/")
class HomeController(
        private val itemService: ItemService,
        private val userService: UserService,
        private val middleManService: MiddleManService
) {

    @Secured("isAnonymous()")
    @View("home")
    @Get
    fun index(principal: Principal?, q: String?, category: String?): Map<String, Any?> = principal.let {
        val user = principal?.name?.let { userService.findUserByEmail(it) }
        val (items, categories) = itemService.listItems(q ?: "", category = category ?: "")
        mapOf(
                "loggedIn" to (it != null),
                "username" to user?.name,
                "items" to items,
                "categories" to categories,
                "totalItems" to itemService.getItemsCount()
        )
    }

    @Secured("isAuthenticated()")
    @View("myads")
    @Get("/myAds")
    fun myAds(principal: Principal): Map<String, Any?> = principal.let {
        val user = requireNotNull(principal.name.let { userService.findUserByEmail(it) })
        mapOf(
                "loggedIn" to true,
                "items" to itemService.getItemsByUser(user.id),
                "username" to user.name
        )
    }

    @Secured("isAuthenticated()")
    @Get("/myNotifications")
    @Produces(MediaType.APPLICATION_JSON)
    fun getNotifications(principal: Principal) : MutableHttpResponse<Any?> = principal.let {
        val user = requireNotNull(principal.name.let { userService.findUserByEmail(it) })
        ok(mapOf("notifications" to middleManService.getNotifications(user)))
    }

    @Secured("isAnonymous()")
    @View("showAd")
    @Get("/ad")
    fun show(principal: Principal?, id: String): Map<String, Any?> = principal.let {
        val user = principal?.name?.let { userService.findUserByEmail(it) }
        val item = requireNotNull(itemService.getItem(id))
        user?.let {
            middleManService.saveInterest(user, item)
        }

        mapOf(
                "loggedIn" to (it != null),
                "username" to user?.name,
                "item" to item,
                "seller" to userService.findUserById(item.userId)?.name
        )
    }

    @Secured("isAuthenticated()")
    @View("mytrades")
    @Get("/myTrades")
    fun showTrades(principal: Principal): Map<String, Any?> = principal.let {
        val user = requireNotNull(principal.name.let { userService.findUserByEmail(it) })
        val trades = middleManService.getTrades(user)

        mapOf(
                "tradesFound" to trades.isNotEmpty(),
                "loggedIn" to true,
                "trades" to trades,
                "username" to user.name
        )
    }

    @Secured("isAuthenticated()")
    @View("mytrade")
    @Get("/myTrade")
    fun showTrade(principal: Principal, id: String): Map<String, Any?> = principal.let {
        val user = requireNotNull(principal.name.let { userService.findUserByEmail(it) })
        val trade = middleManService.getTrade(id)

        val stepsWithName = trade.steps.map {
            val buyerName = requireNotNull(userService.findUserById(it.userId)).name
            val sellerName = requireNotNull(userService.findUserById(it.interest.itemUserid)).name
            TradeStepWithUserName(it.userId, it.interest, buyerName, sellerName)
        }

        mapOf(
                "loggedIn" to true,
                "steps" to stepsWithName,
                "summary" to trade.toSummary(user.id),
                "username" to user.name
        )
    }

    @Secured("isAuthenticated()")
    @Post("/updateTrade")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun updateTrade(principal: Principal, @Body update: TradeUpdate): MutableHttpResponse<Any?> = principal.let {
        val user = requireNotNull(principal.name.let { userService.findUserByEmail(it) })
        middleManService.updateStatus(user, update.id, TradeOpportunityStatus.valueOf(update.status))
        ok(emptyMap<String, String>())
    }

}