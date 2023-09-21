package demo.security.domain.member.eventlistener

data class SignupEvent(
    val receiver: String,
    val title: String,
    val content: String
)