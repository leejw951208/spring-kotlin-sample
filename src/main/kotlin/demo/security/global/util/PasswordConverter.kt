package demo.security.global.util

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Convert
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.password.PasswordEncoder

@Convert
class PasswordConverter(
    private val passwordEncoder: PasswordEncoder
) : AttributeConverter<String, String> {

    override fun convertToDatabaseColumn(attribute: String): String {
        return passwordEncoder.encode(attribute)
    }

    override fun convertToEntityAttribute(dbData: String): String {
        return dbData;
    }
}