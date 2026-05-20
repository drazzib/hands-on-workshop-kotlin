package org.devsummit.workshop.kotlin.shop.service

import org.springframework.http.HttpStatusCode
import org.springframework.http.client.ClientHttpResponse

class WebClientApiErrorHelper {
    companion object {
        fun throwException(res: ClientHttpResponse) {
            val errorBody = res.body.bufferedReader().use { it.readText() }
            if (errorBody.isEmpty()) {
                throw HttpClientException(res.statusCode, "Unknown reason")
            } else {
                throw HttpClientException(res.statusCode, errorBody)
            }
        }
    }
}

data class HttpClientException(val statusCode: HttpStatusCode, override val message: String) : Exception(message)