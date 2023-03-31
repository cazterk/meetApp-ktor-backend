package app.netlify.cazterk.data.user

import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId
import org.litote.kmongo.toId

class MongoUserDataSource(
    db: CoroutineDatabase
) : UserDataSource {
    private val users = db.getCollection<User>()
    override suspend fun getUserByUsername(username: String): User? {
        return users.findOne(User::username eq username)
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }

    override suspend fun getUserById(id: String): User? {
        val bsonId: Id<User> = ObjectId(id).toId()
        return users
            .findOne(User::id eq bsonId)
    }

    override suspend fun getUsers(): List<User> {
        return users.find().toList()
    }
}