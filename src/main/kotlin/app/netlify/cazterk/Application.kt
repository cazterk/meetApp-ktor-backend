package app.netlify.cazterk

import app.netlify.cazterk.data.user.MongoUserDataSource
import app.netlify.cazterk.data.user.User
import io.ktor.server.application.*
import app.netlify.cazterk.plugins.*
import app.netlify.cazterk.security.hashing.SH256HashingService
import app.netlify.cazterk.security.token.JwtTokenService
import app.netlify.cazterk.security.token.TokenConfig
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val mongoPassword = System.getenv("MONGO_PASSWORD")//?: throw IllegalArgumentException("MONGO_PASSWORD not set")
    val dbName = "meetapp-db"
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://cazterk:$mongoPassword@cluster0.zlbzxw2.mongodb.net/$dbName?retryWrites=true&w=majority"
    ).coroutine.getDatabase(dbName)

    val userDatabase = MongoUserDataSource(db)
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")//?: throw IllegalArgumentException("JWT_SECRET not set")
    )
    val hashingService = SH256HashingService()

    configureSecurity(tokenConfig)
    configureRouting(userDatabase, hashingService, tokenService, tokenConfig)
    configureSerialization()
    configureMonitoring()

}
