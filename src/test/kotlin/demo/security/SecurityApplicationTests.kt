package demo.security

import demo.security.jwt.JwtProperties
import demo.security.jwt.JwtProvider
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SecurityApplicationTests {

	private val jwtProvider: JwtProvider = JwtProvider(
		JwtProperties(
			"asd2103_+dA9sD01casn89423Bh43298-d21evV537",
			"Bearer ",
			"Authorization",
			"ljw",
			"kotiln",
			1,
			168
		)
	)

	@Test
	fun contextLoads() {
		val createAccessToken = jwtProvider.createAccessToken("leejw951208@gmail.com")
		println(createAccessToken)
	}

}
