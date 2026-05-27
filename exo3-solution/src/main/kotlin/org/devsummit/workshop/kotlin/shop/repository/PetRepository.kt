package org.devsummit.workshop.kotlin.shop.repository

import org.devsummit.workshop.kotlin.shop.domain.Pet
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PetRepository : CoroutineCrudRepository<Pet, Long>
