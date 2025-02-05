package demo.kotlinboilerplate.global.eventlistener.signup

import demo.kotlinboilerplate.global.mail.MailDto
import demo.kotlinboilerplate.global.mail.MailService
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
    private val mailService: MailService,
    private val redisService: RedisService
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Order(1)
    @Async
    fun saveNumber(event: SignupApproveEvent) {
        redisService.setAuthNumber(event.approveNumber.toString(), event.email)
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Order(1)
    @Async
    fun sendEmail(event: SignupEmailSendEvent) {
        mailService.send(MailDto(event.receiver, event.title, event.content))
    }
}