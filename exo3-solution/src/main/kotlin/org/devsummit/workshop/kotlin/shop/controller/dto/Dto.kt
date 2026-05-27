package org.devsummit.workshop.kotlin.shop.controller.dto

import org.devsummit.workshop.kotlin.shop.domain.Pet

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

fun Pet.toDto() = PetDto(
    id = this.id.value,
    name = this.name.value,
    kind = this.kind.name,
    price = this.price.value,
    currency = this.currency.value,
)
