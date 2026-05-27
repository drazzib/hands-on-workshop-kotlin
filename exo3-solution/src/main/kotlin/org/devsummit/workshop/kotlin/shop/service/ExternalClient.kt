package org.devsummit.workshop.kotlin.shop.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.devsummit.workshop.kotlin.shop.domain.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull

@Service
class ExternalClient(private val petNameWebClient: WebClient) {
    suspend fun fetchRandomName(kind: PetKind): Either<PetNameError, PetName> = try {
        val dto: RemotePetNameDto? = petNameWebClient
            .get()
            .uri { it.path("/api/v1/pet-name")
                .queryParam("kind", kind).build()
            }
            .retrieve()
            .onStatus({ t -> t.isError }) { res ->
                WebClientApiErrorHelper.createMonoError(res)
            }
            .awaitBodyOrNull()
        dto?.name?.let { PetName(it) }?.right() ?: PetNameUnhandledError(Exception("Empty name response")).left()
    } catch (e: HttpClientException) {
        when (e.statusCode) {
            HttpStatus.GATEWAY_TIMEOUT -> PetNameTimeout(kind).left()
            else -> PetNameUnhandledError(e).left()
        }
    } catch (e: Exception) {
        PetNameUnhandledError(e).left()
    }

    suspend fun fetchPetPrice(kind: PetKind): Either<PriceV1UnhandledError, PetPrice> = try {
        petNameWebClient
            .get()
            .uri { it.path("/api/v1/pet-price")
                .queryParam("kind", kind).build()
            }
            .retrieve()
            .onStatus({ t -> t.isError }) { res ->
                WebClientApiErrorHelper.createMonoError(res)
            }
            .awaitBodyOrNull<RemotePetPriceDto>()
            ?.let { PetPrice(Price(it.price), Currency(it.currency)) }?.right()
            ?: PriceV1UnhandledError(Exception("Empty price response")).left()
    } catch (e: Exception) {
         PriceV1UnhandledError(e).left()
    }
    suspend fun fetchPetPriceV2(kind: PetKind): Either<PriceV2Error, PetPrice> = try {
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
<<<<<<< Updated upstream
            ?.let { PetPrice(Price(it.price), Currency(it.currency)) } ?: throw PriceV2UnhandledError(Exception("Empty price response"))
=======
            ?.let { PetPrice(Price(it.price), Currency(it.currency)) }?.right() ?: PriceV2UnhandledError(Exception("Empty price response")).left()
>>>>>>> Stashed changes
    } catch (e: HttpClientException) {
        when (e.statusCode) {
            HttpStatus.NOT_FOUND -> PriceV2NotFoundError(e.message).left()
            else -> PriceV2UnhandledError(e).left()
        }
    } catch (e: Exception) {
         PriceV2UnhandledError(e).left()
    }

}

data class RemotePetNameDto(val name: String)
data class RemotePetPriceDto(val price: Double, val currency: String)

