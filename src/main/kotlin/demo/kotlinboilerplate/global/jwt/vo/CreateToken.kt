package demo.kotlinboilerplate.global.jwt.vo

data class CreateToken (
    val accessToken: String,
    val refreshToken: String,
) {
}