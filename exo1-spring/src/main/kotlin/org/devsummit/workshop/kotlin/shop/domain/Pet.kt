package org.devsummit.workshop.kotlin.shop.domain

import jakarta.persistence.*
import org.devsummit.workshop.kotlin.shop.controller.dto.PetDto

@Table(name = "pets")
@Entity
data class Pet(
    // EXO 1.0: review domain field types
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id val id: Long? = null,
    // EXO 1.6: replace with value classes
    val name: String,
    // EXO 1.1: replace String with PetKind
    val kind: String,
    val price: Double,
    val currency: String,

) {
    // EXO 1.2: Move this mapper as an extension function in dto/Dto.kt
    fun asDto(): PetDto {
        return PetDto(
            id = this.id,
            name = this.name,
            kind = this.kind,
            price = this.price,
            currency = this.currency,
        )
    }
}

data class NewPet(
    // EXO 1.4: make name nullable
    val name: String,
    // EXO 1.1: replace String with PetKind
    val kind: String,
)
