package app.netlify.cazterk

import app.netlify.cazterk.data.user.MongoUserDataSource
import app.netlify.cazterk.data.user.User
import io.ktor.server.application.*
import app.netlify.cazterk.plugins.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val mongoPassword = System.getenv("MONGO_PASSWORD")
    val dbName = "meetapp-db"
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://cazterk:$mongoPassword@cluster0.zlbzxw2.mongodb.net/$dbName?retryWrites=true&w=majority"
    ).coroutine.getDatabase(dbName)

    val userDatabase = MongoUserDataSource(db)



    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}
