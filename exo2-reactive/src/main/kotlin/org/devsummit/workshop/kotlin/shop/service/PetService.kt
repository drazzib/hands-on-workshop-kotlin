package org.devsummit.workshop.kotlin.shop.service

import org.devsummit.workshop.kotlin.shop.domain.NewPet
import org.devsummit.workshop.kotlin.shop.domain.Pet
import org.devsummit.workshop.kotlin.shop.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class PetService(
    private val repository: PetRepository,
    private val externalClient: ExternalClient
) {

    // EXO 2.1: add suspend for blocking JPA calls wrap
    // EXO 2.4: once migrated to R2DBC, remove the withContext
    fun findAll() = repository.findAll()

    // EXO 2.1: add suspend
    fun createPet(newPet: NewPet): Pet {
        // EXO 2.5: wrap both externalClient calls in coroutineScope { } and async { }
        val petName = newPet.name ?: externalClient.fetchRandomName(newPet.kind)
        // EXO 2.6: inside the async and multiple try
        val petPrice = externalClient.fetchPetPrice(newPet.kind)
        // EXO 2.4: remove withContext
        val pet = Pet(name = petName, kind = newPet.kind, price = petPrice.price, currency = petPrice.currency)
        return repository.save(pet)
    }

}