package org.devsummit.workshop.kotlin.shop.controller

import org.devsummit.workshop.kotlin.shop.TestcontainersConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.client.RestTestClient

@Import(TestcontainersConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class PetControllerIntegrationTest(@Autowired private val webTestClient: RestTestClient) {

    @Test
    fun `list pets returns initialized database initially`() {
        webTestClient.get().uri("/api/pets")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.length()").isEqualTo(3)
            .jsonPath("$[0].name").isEqualTo("Buddy")
            .jsonPath("$[1].name").isEqualTo("Mittens")
            .jsonPath("$[2].name").isEqualTo("Twilight Sparkle")
    }
}