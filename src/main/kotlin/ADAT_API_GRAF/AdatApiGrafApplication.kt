package ADAT_API_GRAF

import ADAT_API_GRAF.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class AdatApiGrafApplication

fun main(args: Array<String>) {
	runApplication<AdatApiGrafApplication>(*args)
}
