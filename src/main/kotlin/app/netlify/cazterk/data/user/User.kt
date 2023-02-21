package app.netlify.cazterk.data.user

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val salt: String,
    @BsonId val id: ObjectId = ObjectId()
) {

}