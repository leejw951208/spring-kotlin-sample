package demo.springkotlinsample.common.jpa

import jakarta.persistence.EntityManager
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class SoftDeleteFilterAspect(
    private val entityManager: EntityManager,
) {
    @Around("@annotation(softDeleteFilter)")
    fun applySoftDeleteFilter(
        joinPoint: ProceedingJoinPoint,
        softDeleteFilter: SoftDeleteFilter,
    ): Any? {
        val session = entityManager.unwrap(org.hibernate.Session::class.java)
        var filterEnabled = false

        if (softDeleteFilter.value) {
            // 삭제된 데이터 제외: 필터 활성화 (deleted = false 조건 적용)
            session.enableFilter("softDeleteFilter").setParameter("deleted", false)
            filterEnabled = true
        } else {
            // 삭제된 데이터 포함: 만약 필터가 활성화되어 있다면 비활성화
            if (session.getEnabledFilter("softDeleteFilter") != null) {
                session.disableFilter("softDeleteFilter")
            }
        }

        return try {
            joinPoint.proceed()
        } finally {
            if (filterEnabled) {
                session.disableFilter("softDeleteFilter")
            }
        }
    }
}
