package org.devsummit.workshop.kotlin.shop.controller

import kotlinx.coroutines.flow.map
import org.devsummit.workshop.kotlin.shop.controller.dto.NewPetDto
import org.devsummit.workshop.kotlin.shop.controller.dto.PetDto
import org.devsummit.workshop.kotlin.shop.controller.dto.toDto
import org.devsummit.workshop.kotlin.shop.domain.NewPet
import org.devsummit.workshop.kotlin.shop.domain.PetKind
import org.devsummit.workshop.kotlin.shop.domain.PetName
import org.devsummit.workshop.kotlin.shop.domain.PetNameTimeout
import org.devsummit.workshop.kotlin.shop.domain.PetNameUnhandledError
import org.devsummit.workshop.kotlin.shop.domain.PriceV1UnhandledError
import org.devsummit.workshop.kotlin.shop.service.PetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pets")
class PetController(private val service: PetService) {

    @GetMapping
    suspend fun listPets()= service.findAll().map { pet -> pet.toDto() }

    @PostMapping
    suspend fun createPet(@RequestBody pet: NewPetDto): ResponseEntity<PetDto> {
        val newPet = NewPet(pet.name?.let { PetName(it) }, PetKind.valueOf(pet.kind))
        return service.createPet(newPet).fold(
            {
                when (it) {
                    is PetNameTimeout -> ResponseEntity.status(504).build()
                    is PetNameUnhandledError -> ResponseEntity.internalServerError().build()
                    is PriceV1UnhandledError -> throw it.exception
                }
            },
            { ResponseEntity.ok(it.toDto()) },
        )
    }
}