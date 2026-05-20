package org.devsummit.workshop.kotlin.shop.controller.dto

data class NewPetDto(
    val name: String?,
    val kind: String,
)

data class PetDto(
    val id: Long?,
    val name: String?,
    val kind: String,
    val price: Double,
    val currency: String,
)