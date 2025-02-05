package demo.kotlinboilerplate.global.eventlistener.signup

data class SignupApproveEvent(
    val email: String
    , val approveNumber: Int
)