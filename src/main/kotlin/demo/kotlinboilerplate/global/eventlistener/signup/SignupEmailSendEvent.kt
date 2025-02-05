package demo.kotlinboilerplate.global.eventlistener.signup

data class SignupEmailSendEvent(
    val receiver: String,
    val title: String,
    val content: String
)