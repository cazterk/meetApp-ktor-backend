package app.netlify.cazterk.data.user


interface UserDataSource {
    suspend fun getUserByUsername(username: String): User?
    suspend fun insertUser(user: User): Boolean
    suspend fun getUserById(id: String): User?
    suspend fun getUsers(): List<User>


}