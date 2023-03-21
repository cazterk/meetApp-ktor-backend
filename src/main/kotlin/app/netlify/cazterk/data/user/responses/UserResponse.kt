package app.netlify.cazterk.data.user.responses

import app.netlify.cazterk.data.user.User
import app.netlify.cazterk.data.user.UserDto


fun User.toDto(): UserDto =
    UserDto(
        id = this.id.toString(),
        username = this.username,
        firstName = this.firstName,
        lastName =  this.lastName,

    )

//fun UserDto.toUser(): User =
//    User(
//        username = this.username,
//        firstName = this.firstName,
//        lastName =  this.lastName,
//        salt = this.salt
//    )