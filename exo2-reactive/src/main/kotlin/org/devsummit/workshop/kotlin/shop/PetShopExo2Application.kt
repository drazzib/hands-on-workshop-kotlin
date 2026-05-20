package org.devsummit.workshop.kotlin.shop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class PetShopExo2Application

fun main(args: Array<String>) {
    runApplication<PetShopExo2Application>(*args)
}
