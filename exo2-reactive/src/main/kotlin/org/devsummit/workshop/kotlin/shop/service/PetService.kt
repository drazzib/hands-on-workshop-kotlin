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

    fun findAll() = repository.findAll()

    fun createPet(newPet: NewPet): Pet {
        val petName = newPet.name ?: externalClient.fetchRandomName(newPet.kind)
        val petPrice = externalClient.fetchPetPrice(newPet.kind)
        val pet = Pet(name = petName, kind = newPet.kind, price = petPrice.price, currency = petPrice.currency)
        return repository.save(pet)
    }

}