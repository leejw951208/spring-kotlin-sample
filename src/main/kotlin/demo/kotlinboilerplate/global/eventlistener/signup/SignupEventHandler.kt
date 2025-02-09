package demo.kotlinboilerplate.global.eventlistener.signup

import demo.kotlinboilerplate.global.redis.RedisService
import org.springframework.core.annotation.Order
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class SignupEventHandler(
    private val redisService: RedisService
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Order(1)
    @Async
    fun saveNumber(event: SignupApproveEvent) {
        redisService.setValue(event.approveNumber.toString(), event.email)
    }
}