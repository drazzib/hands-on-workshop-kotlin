package org.devsummit.workshop.kotlin.shop.service

import org.devsummit.workshop.kotlin.shop.domain.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class ExternalClient(private val petNameWebClient: WebClient) {
    fun fetchRandomName(kind: PetKind): PetName = try {
        petNameWebClient
            .get()
            .uri { it.path("/api/v1/pet-name")
                    .queryParam("kind", kind).build()
            }
            .retrieve()
            .onStatus({ t -> t.isError }) { res ->
                WebClientApiErrorHelper.createMonoError(res)
            }
            .bodyToMono<RemotePetNameDto>()
            .block()
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
            .onStatus({ t -> t.isError }) { res ->
                WebClientApiErrorHelper.createMonoError(res)
            }
            .bodyToMono<RemotePetPriceDto>()
            .block()
            ?.let { PetPrice(Price(it.price), Currency(it.currency)) }
            ?: throw PriceV1UnhandledError(Exception("Empty price response"))
    } catch (e: Exception) {
        throw PriceV1UnhandledError(e)
    }
    suspend fun fetchPetPriceV2(kind: PetKind): PetPrice = try {
        petNameWebClient
            .get()
            .uri { it.path("/api/v2/pet-price")
                .queryParam("kind", kind).build()
            }
            .retrieve()
            .onStatus({ t -> t.isError }) {
                WebClientApiErrorHelper.createMonoError(it)
            }
            .awaitBodyOrNull<RemotePetPriceDto>()
            ?.let { PetPrice(Price(it.price), Currency(it.currency)) } ?: throw PriceV1UnhandledError(Exception("Empty price response"))
    } catch (e: HttpClientException) {
        when (e.statusCode) {
            HttpStatus.NOT_FOUND -> throw PriceV2NotFoundError(e.message)
            else -> throw PriceV2UnhandledError(e)
        }
    } catch (e: Exception) {
        throw PriceV2UnhandledError(e)
    }

}

data class RemotePetNameDto(val name: String)
data class RemotePetPriceDto(val price: Double, val currency: String)

