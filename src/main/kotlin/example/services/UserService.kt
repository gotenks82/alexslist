package example.services

import example.models.User
import javax.inject.Singleton

@Singleton
class UserService {

    private val users: List<User> = createUsers()

    fun findUserById(id: String): User? {
        return users.find { it.id == id }
    }

    fun findUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    private fun createUsers(): List<User> {
        return listOf(
                User(email = "alex@test.com", id = "1", name = "Alex"),
                User(email = "hannah@test.com", id = "2", name = "Hannah"),
                User(email = "david@test.com", id = "3", name = "David"),
                User(email = "kathy@test.com", id = "4", name = "Kathy"),
                User(email = "5@test.com", id = "5", name = "User5"),
                User(email = "6@test.com", id = "6", name = "User6"),
                User(email = "7@test.com", id = "7", name = "User7"),
                User(email = "8@test.com", id = "8", name = "User8"),
                User(email = "9@test.com", id = "9", name = "User9"),
                User(email = "10@test.com", id = "10", name = "User10")
        )
    }
}