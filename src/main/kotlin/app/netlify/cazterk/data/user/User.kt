package app.netlify.cazterk.data.user

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id


data class User(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val salt: String,
    @BsonId val id: Id<User>? = null,
)

@Serializable
data class UserDto(
    val username: String,
    val firstName: String,
    val lastName: String,
    val id: String? = null
)