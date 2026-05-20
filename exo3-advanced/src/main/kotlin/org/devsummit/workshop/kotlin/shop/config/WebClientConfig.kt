package org.devsummit.workshop.kotlin.shop.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(PetNameGeneratorProperties::class)
class WebClientConfig {
    @Bean
    fun petNameWebClient(props: PetNameGeneratorProperties): WebClient =
        WebClient.builder()
            .baseUrl(props.baseUrl.trimEnd('/'))
            .build()
}

