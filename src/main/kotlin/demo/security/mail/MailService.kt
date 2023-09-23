package demo.security.mail

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(
    private val javaMailSender: JavaMailSender
) {

    fun send(mailDto: MailDto) {
        val simpleMailMessage = SimpleMailMessage()
        simpleMailMessage.subject = mailDto.title
        simpleMailMessage.text = mailDto.content
        simpleMailMessage.setTo(mailDto.receiver)

        javaMailSender.send(simpleMailMessage)
    }
}