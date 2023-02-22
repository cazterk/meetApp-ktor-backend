package app.netlify.cazterk.plugins

import app.netlify.cazterk.authenticate
import app.netlify.cazterk.data.user.UserDataSource
import app.netlify.cazterk.getSecretInfo
import app.netlify.cazterk.security.hashing.HashingService
import app.netlify.cazterk.security.token.TokenConfig
import app.netlify.cazterk.security.token.TokenService
import app.netlify.cazterk.signIn
import app.netlify.cazterk.signUp
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        signIn(userDataSource, hashingService, tokenService, tokenConfig)
        signUp(hashingService, userDataSource)
        authenticate()
        getSecretInfo()
    }
}
