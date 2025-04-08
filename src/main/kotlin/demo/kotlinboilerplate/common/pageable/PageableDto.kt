package demo.kotlinboilerplate.common.pageable

import kotlin.math.ceil

class PageableDto<T : List<*>>(
    val data: T,
    val page: Int,
    val size: Int,
    val totalCount: Int,
) {
    val maxPage: Int = if (size != 0) ceil(totalCount.toDouble() / size).toInt() else 0
}
