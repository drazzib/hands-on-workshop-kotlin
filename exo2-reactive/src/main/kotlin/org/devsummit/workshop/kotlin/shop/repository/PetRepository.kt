package org.devsummit.workshop.kotlin.shop.repository

import org.devsummit.workshop.kotlin.shop.domain.Pet
import org.springframework.data.jpa.repository.JpaRepository

// EXO 2.4: replace JpaRepository with CoroutineCrudRepository
interface PetRepository : JpaRepository<Pet, Long>
