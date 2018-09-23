package example.services

import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UserDetails
import io.reactivex.Flowable
import org.reactivestreams.Publisher

import javax.inject.Singleton
import java.util.ArrayList

@Singleton
class AuthenticationProviderUserPassword(
        private val userService: UserService
) : AuthenticationProvider {
    override fun authenticate(authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse> {
        if (userService.findUserByEmail(authenticationRequest.identity as String) != null) {
            val userDetails = UserDetails(authenticationRequest.identity as String, ArrayList())
            return Flowable.just(userDetails)
        }
        return Flowable.just(AuthenticationFailed())
    }
}