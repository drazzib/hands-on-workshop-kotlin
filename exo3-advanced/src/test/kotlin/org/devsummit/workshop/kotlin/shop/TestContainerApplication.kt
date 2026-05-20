package org.devsummit.workshop.kotlin.shop

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<PetShopExo3Application>().with(TestcontainersConfiguration::class).run(*args)
}
