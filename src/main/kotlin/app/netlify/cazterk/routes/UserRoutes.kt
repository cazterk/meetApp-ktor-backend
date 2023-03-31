package app.netlify.cazterk.routes


import app.netlify.cazterk.data.user.User
import app.netlify.cazterk.data.user.UserDataSource
import app.netlify.cazterk.data.user.responses.toDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.json


fun Route.getUserById(userDataSource: UserDataSource) {
    get("user/{id}") {

        val userId = call.parameters["id"].toString()
        val user = userDataSource.getUserById(userId)
        user
            ?.let { foundUser ->
                call.respond(foundUser.toDto())
            }
            ?: call.respond(HttpStatusCode.NotFound, "No user with that id $userId was found")

    }
}

fun Route.getUsers(userDataSource: UserDataSource) {
    get("users") {
        val users = userDataSource.getUsers()
            .map(User::toDto)
        call.respond(users)


    }
}


