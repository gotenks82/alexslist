package example.controllers


import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.Secured
import io.micronaut.views.View

import java.util.Collections
import java.util.HashMap

@Secured("isAnonymous()")
@Controller("/login")
class LoginAuthController {

    @Get("/auth")
    @View("auth")
    fun auth(): Map<String, Any> {
        return HashMap()
    }

    @Get("/authFailed")
    @View("auth")
    fun authFailed(): Map<String, Any> {
        return Collections.singletonMap<String, Any>("errors", true)
    }
}