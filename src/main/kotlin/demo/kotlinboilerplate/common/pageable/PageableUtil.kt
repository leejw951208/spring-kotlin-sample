package demo.kotlinboilerplate.common.pageable

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.util.Locale

object PageableUtil {
    fun createPageable(
        page: Int,
        size: Int,
        order: String,
        orderBy: String,
    ): Pageable {
        val sortable = Sort.by(Sort.Direction.valueOf(order.uppercase(Locale.getDefault())), orderBy)
        return PageRequest.of(page - 1, size, sortable)
    }
}
