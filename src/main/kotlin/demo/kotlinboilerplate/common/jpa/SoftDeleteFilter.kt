package demo.kotlinboilerplate.common.jpa

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SoftDeleteFilter(
    val value: Boolean = false,
)
