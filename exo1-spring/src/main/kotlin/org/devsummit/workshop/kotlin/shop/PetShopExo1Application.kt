package org.devsummit.workshop.kotlin.shop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class PetShopExo1Application

fun main(args: Array<String>) {
    runApplication<org.devsummit.workshop.kotlin.shop.PetShopExo1Application>(*args)
}
