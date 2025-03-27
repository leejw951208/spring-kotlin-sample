package demo.kotlinboilerplate.user.user.repository

import demo.kotlinboilerplate.user.user.domain.User

interface UserRepository {
    fun findOne(id: Long): User?
    fun findOne(email: String): User?
    fun save(user: User): User
}
