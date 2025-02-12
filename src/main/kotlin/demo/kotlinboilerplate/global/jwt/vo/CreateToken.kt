package demo.kotlinboilerplate.global.jwt.vo

class CreateToken (
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun from(accessToken: String, refreshToken: String): CreateToken {
            return CreateToken(accessToken, refreshToken)
        }
    }
}