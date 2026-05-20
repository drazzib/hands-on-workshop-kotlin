package org.devsummit.workshop.kotlin.shop.repository

import org.devsummit.workshop.kotlin.shop.domain.Pet
import org.springframework.data.jpa.repository.JpaRepository

interface PetRepository : JpaRepository<Pet, Long>
