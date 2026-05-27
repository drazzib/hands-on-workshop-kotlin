package org.devsummit.workshop.kotlin.shop.service

import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class WebClientApiErrorHelper {
    companion object {
        fun createMonoError(res: ClientResponse): Mono<Throwable> {
            return res.bodyToMono<String>()
                .switchIfEmpty { Mono.error(HttpClientException(res.statusCode(), "Unknown reason")) }
                .flatMap { errorBody ->
                    Mono.error(HttpClientException(res.statusCode(), errorBody))
                }
        }
    }
}

data class HttpClientException(val statusCode: HttpStatusCode, override val message: String) : Exception(message)