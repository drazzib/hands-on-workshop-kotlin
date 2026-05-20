package org.devsummit.workshop.kotlin.shop.service

import org.devsummit.workshop.kotlin.shop.domain.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class ExternalClient(private val petNameWebClient: RestClient) {
    fun fetchRandomName(kind: PetKind): PetName = try {
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
            ?.name?.let { PetName(it) } ?: throw PetNameUnhandledError(Exception("Empty name response"))
    } catch (e: HttpClientException) {
        when (e.statusCode) {
            HttpStatus.GATEWAY_TIMEOUT -> throw PetNameTimeout(kind)
            else -> throw PetNameUnhandledError(e)
        }
    } catch (e: Exception) {
        throw PetNameUnhandledError(e)
    }

    fun fetchPetPrice(kind: PetKind): PetPrice = try {
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
            ?.let { PetPrice(Price(it.price), Currency(it.currency)) }
            ?: throw PriceV1UnhandledError(Exception("Empty price response"))
    } catch (e: Exception) {
        throw PriceV1UnhandledError(e)
    }

}

data class RemotePetNameDto(val name: String)
data class RemotePetPriceDto(val price: Double, val currency: String)

