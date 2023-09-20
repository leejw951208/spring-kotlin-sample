package demo.security.global.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Convert
import org.springframework.security.crypto.bcrypt.BCrypt

@Convert
class PasswordConverter: AttributeConverter<String, String> {

    override fun convertToDatabaseColumn(attribute: String): String {
        return BCrypt.hashpw(attribute, BCrypt.gensalt())
    }

    override fun convertToEntityAttribute(dbData: String): String {
        return dbData;
    }
}