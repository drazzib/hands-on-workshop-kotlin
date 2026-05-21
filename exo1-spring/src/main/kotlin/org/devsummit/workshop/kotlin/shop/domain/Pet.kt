package org.devsummit.workshop.kotlin.shop.domain

import jakarta.persistence.*
import org.devsummit.workshop.kotlin.shop.controller.dto.PetDto

@Table(name = "pets")
@Entity
data class Pet(

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id val id: Long? = null,
    val name: String,
    val kind: String,
    val price: Double,
    val currency: String,

) {
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
    val name: String,
    val kind: String,
)
