package org.devsummit.workshop.kotlin.shop.service

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.devsummit.workshop.kotlin.shop.domain.NewPet
import org.devsummit.workshop.kotlin.shop.domain.Pet
import org.devsummit.workshop.kotlin.shop.domain.PetKind
import org.devsummit.workshop.kotlin.shop.domain.PetPrice
import org.devsummit.workshop.kotlin.shop.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class PetService(
    private val repository: PetRepository,
    private val externalClient: ExternalClient
) {
    private val logger = KotlinLogging.logger {}
    suspend fun findAll() =
        repository.findAll()

    suspend fun createPet(newPet: NewPet): Pet = coroutineScope {
        val petName = async { newPet.name ?: externalClient.fetchRandomName(newPet.kind) }
        val petPrice = async { fetchPetPrice(newPet.kind) }.await()
        val pet = Pet(name = petName.await(), kind = newPet.kind, price = petPrice.price, currency = petPrice.currency)
        repository.save(pet)
    }

    suspend fun fetchPetPrice(kind: PetKind): PetPrice {
        return try {
            logger.info { "Fetching price for $kind from v2 API" }
            externalClient.fetchPetPriceV2(kind)
        } catch (e: Exception) {
            logger.error(e) {"Error fetching price from v2, falling back to v1: ${e.message}"}
            externalClient.fetchPetPrice(kind)
        }
    }
}