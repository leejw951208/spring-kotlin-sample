package demo.security.member.eventlistener

data class SignupEvent(
    val receiver: String,
    val title: String,
    val content: String,
    val certifiedNumber: Int
)