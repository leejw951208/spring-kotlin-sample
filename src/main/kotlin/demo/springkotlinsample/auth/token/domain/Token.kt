package demo.springkotlinsample.auth.token.domain

class Token(
    val id: Long?,
    val userId: Long,
    var refreshToken: String,
) {
    fun refresh(refreshToken: String) {
        this.refreshToken = refreshToken
    }
}
