package org.devsummit.workshop.kotlin.shop.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "client.pet-name-generator")
data class PetNameGeneratorProperties(
    val baseUrl: String
)

