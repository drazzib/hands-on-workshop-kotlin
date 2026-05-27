package org.devsummit.workshop.kotlin.shop.service

import org.devsummit.workshop.kotlin.shop.domain.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class ExternalClient(private val petNameWebClient: WebClient) {
    // EXO 2.2: add suspend and replace .bodyToMono().block() with `.awaitBodyOrNull
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
            // EXO 2.2: replace with `.awaitBodyOrNull
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

    // EXO 2.3: add suspend replace .bodyToMono.block() .awaitBodyOrNull
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
            // EXO 2.3: replace with .awaitBodyOrNull
            .bodyToMono<RemotePetPriceDto>()
            .block()
            ?.let { PetPrice(Price(it.price), Currency(it.currency)) }
            ?: throw PriceV1UnhandledError(Exception("Empty price response"))
    } catch (e: Exception) {
        throw PriceV1UnhandledError(e)
    }

    // EXO 2.6: Use V2 method inside PetService
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

