package org.devsummit.workshop.kotlin.shop.service

import org.devsummit.workshop.kotlin.shop.domain.PetNameTimeout
import org.devsummit.workshop.kotlin.shop.domain.PetNameUnhandledError
import org.devsummit.workshop.kotlin.shop.domain.PriceV1UnhandledError
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class ExternalClient(private val petNameWebClient: RestClient) {
    // EXO 1.6: evolve parameter/return types to value classes after introducing them.
    fun fetchRandomName(kind: String): String = try {
        petNameWebClient
            .get()
            .uri { it.path("/api/v1/pet-name")
                    .queryParam("kind", kind).build()
            }
            .retrieve()
            .onStatus({ t -> t.isError }) { _, res ->
                WebClientApiErrorHelper.throwException(res)
            }
            .body<RemotePetNameDto>()
            ?.name ?: throw PetNameUnhandledError(Exception("Empty name response"))
    } catch (e: HttpClientException) {
        when (e.statusCode) {
            HttpStatus.GATEWAY_TIMEOUT -> throw PetNameTimeout(kind)
            else -> throw PetNameUnhandledError(e)
        }
    } catch (e: Exception) {
        throw PetNameUnhandledError(e)
    }

    // EXO 1.1 + 1.5: kind should become PetKind and is used to fetch remote pricing.
    fun fetchPetPrice(kind: String): RemotePetPriceDto = try {
        petNameWebClient
            .get()
            .uri { it.path("/api/v1/pet-price")
                    .queryParam("kind", kind).build()
            }
            .retrieve()
            .onStatus({ t -> t.isError }) { _, res ->
                WebClientApiErrorHelper.throwException(res)
            }
            .body<RemotePetPriceDto>()
            ?: throw PriceV1UnhandledError(Exception("Empty price response"))
    } catch (e: Exception) {
        throw PriceV1UnhandledError(e)
    }
}

data class RemotePetNameDto(val name: String)
data class RemotePetPriceDto(val price: Double, val currency: String)

