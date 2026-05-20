package org.devsummit.workshop.kotlin.shop.service

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.devsummit.workshop.kotlin.shop.domain.NewPet
import org.devsummit.workshop.kotlin.shop.domain.Pet
import org.devsummit.workshop.kotlin.shop.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class PetService(
    private val repository: PetRepository,
    private val externalClient: ExternalClient
) {

    suspend fun findAll() = repository.findAll()

    suspend fun createPet(newPet: NewPet): Pet = coroutineScope {
        val generatedNameDeferred = async { newPet.name ?:
            externalClient.fetchRandomName(newPet.kind)
        }
        val petPrice = async {
            try {
                externalClient.fetchPetPriceV2(newPet.kind)
            } catch (e: Exception) {
                externalClient.fetchPetPrice(newPet.kind)
            }
        }.await()
        val pet = Pet(name = generatedNameDeferred.await(), kind = newPet.kind, price = petPrice.price, currency = petPrice.currency)
        return@coroutineScope repository.save(pet)
    }
}