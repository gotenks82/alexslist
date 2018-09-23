package example.controllers

import example.services.ItemService
import example.services.UserService
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Controller
import io.micronaut.security.Secured
import io.micronaut.views.View
import java.security.Principal

@Controller("/")
class HomeController(
        private val itemService: ItemService,
        private val userService: UserService
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

    @Secured("isAnonymous()")
    @View("showAd")
    @Get("/ad")
    fun show(principal: Principal?, id: String): Map<String, Any?> = principal.let {
        val user = principal?.name?.let { userService.findUserByEmail(it) }
        val item = requireNotNull(itemService.getItem(id))
        mapOf(
                "loggedIn" to (it != null),
                "username" to user?.name,
                "item" to item,
                "seller" to userService.findUserById(item.userId)?.name
        )
    }

}