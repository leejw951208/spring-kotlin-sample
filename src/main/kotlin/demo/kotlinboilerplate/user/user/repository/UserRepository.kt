package demo.kotlinboilerplate.user.user.repository

import demo.kotlinboilerplate.user.user.domain.User

interface UserRepository {
    fun findUser(id: Long): User?

    fun findUser(email: String): User?

    fun save(user: User): User
}
