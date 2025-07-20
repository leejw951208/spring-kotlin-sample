package demo.springkotlinsample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class SpringKotlinSample

fun main(args: Array<String>) {
    runApplication<SpringKotlinSample>(*args)
}
