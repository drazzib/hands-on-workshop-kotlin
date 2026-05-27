package org.devsummit.workshop.kotlin.shop.controller.dto

data class NewPetDto(
    // EXO 1.4: make name nullable
    val name: String,
    // EXO 1.1: replace with PetKind
    val kind: String,
)

data class PetDto(
    // EXO 1.0: keep DTO types aligned with domain model updates.
    val id: Long?,
    val name: String,
    // EXO 1.1: replace String with PetKind
    val kind: String,
    val price: Double,
    val currency: String,
)

// EXO 1.2: add extension mapping here (fun Pet.toDto()) and remove mapping from Pet.kt.
