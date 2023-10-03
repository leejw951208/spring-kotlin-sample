package demo.security.global.eventlistener.signup

import demo.security.global.mail.MailDto
import demo.security.global.mail.MailService
import demo.security.global.redis.RedisService
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
    fun saveNumberToRedis(event: SignupVerificationEvent) {
        redisService.setValue(event.receiver, event.verificationNumber.toString())
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Order(2)
    @Async
    fun sendEmail(event: SignupVerificationEvent) {
        mailService.send(MailDto(event.receiver, event.title, event.content))
    }
}