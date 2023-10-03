package demo.security.global.eventlistener.signup

data class SignupVerificationEvent(
    val receiver: String?
    , val title: String?
    , val content: String?
    , val verificationNumber: Int?
)