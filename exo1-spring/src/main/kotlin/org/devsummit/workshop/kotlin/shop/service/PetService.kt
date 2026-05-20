package org.devsummit.workshop.kotlin.shop.service

import org.devsummit.workshop.kotlin.shop.domain.NewPet
import org.devsummit.workshop.kotlin.shop.domain.Pet
import org.devsummit.workshop.kotlin.shop.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class PetService(private val repository: PetRepository) {

     fun findAll() = repository.findAll()

     fun createPet(newPet: NewPet): Pet  {
        val pet = Pet(name = newPet.name, kind = newPet.kind, price = 100.0, currency = "EUR")
        return repository.save(pet)
    }

}