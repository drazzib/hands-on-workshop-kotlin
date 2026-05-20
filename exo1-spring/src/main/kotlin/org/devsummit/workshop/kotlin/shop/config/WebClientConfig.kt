package org.devsummit.workshop.kotlin.shop.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
@EnableConfigurationProperties(PetNameGeneratorProperties::class)
class WebClientConfig {
    @Bean
    fun petNameWebClient(props: PetNameGeneratorProperties): RestClient =
        RestClient.builder()
            .baseUrl(props.baseUrl.trimEnd('/'))
            .build()
}

