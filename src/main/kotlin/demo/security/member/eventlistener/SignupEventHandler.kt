package demo.security.member.eventlistener

import demo.security.mail.MailDto
import demo.security.mail.MailService
import demo.security.redis.RedisService
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
    fun savedNumberToRedis(event: SignupEvent) {
        redisService.setValue(event.receiver, event.certifiedNumber.toString())
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Order(2)
    @Async
    fun sendEmail(event: SignupEvent) {
        mailService.send(MailDto(event.receiver, event.title, event.content))
    }
}