package org.devsummit.workshop.kotlin.shop.controller

import org.devsummit.workshop.kotlin.shop.controller.dto.NewPetDto
import org.devsummit.workshop.kotlin.shop.controller.dto.PetDto
import org.devsummit.workshop.kotlin.shop.controller.dto.toDto
import org.devsummit.workshop.kotlin.shop.domain.NewPet
import org.devsummit.workshop.kotlin.shop.domain.PetKind
import org.devsummit.workshop.kotlin.shop.domain.PetName
import org.devsummit.workshop.kotlin.shop.service.PetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pets")
class PetController(private val service: PetService) {

    @GetMapping
    fun listPets() = service.findAll().map { it.toDto() }

    @PostMapping
    fun createPet(@RequestBody pet: NewPetDto): ResponseEntity<PetDto> {
        val newPet = NewPet(pet.name?.let { PetName(it) }, PetKind.valueOf(pet.kind))
        return service.createPet(newPet).let { ResponseEntity.ok(it.toDto()) }
    }
}