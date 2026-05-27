package org.devsummit.workshop.kotlin.shop.service

import org.devsummit.workshop.kotlin.shop.domain.NewPet
import org.devsummit.workshop.kotlin.shop.domain.Pet
import org.devsummit.workshop.kotlin.shop.repository.PetRepository
import org.springframework.stereotype.Service

@Service
// EXO 1.3: inject ExternalClient through constructor as a private val.
class PetService(private val repository: PetRepository) {

     fun findAll() = repository.findAll()

     fun createPet(newPet: NewPet): Pet  {
        // EXO 1.4: if newPet.name is null, call externalClient.fetchRandomName(...).
        // EXO 1.5: replace hard-coded price/currency with externalClient.fetchPetPrice(...).
        // EXO 1.6: keep this creation aligned with value class wrappers.
        val pet = Pet(name = newPet.name, kind = newPet.kind, price = 100.0, currency = "EUR")
        return repository.save(pet)
    }

}