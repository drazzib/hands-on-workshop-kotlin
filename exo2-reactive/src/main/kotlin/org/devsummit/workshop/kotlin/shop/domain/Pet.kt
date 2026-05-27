package org.devsummit.workshop.kotlin.shop.domain

import jakarta.persistence.*
import org.devsummit.workshop.kotlin.shop.controller.dto.PetDto

// EXO 2.4: swap Jakarta Persistence annotations for Spring Data relational ones
@Table(name = "pets")
@Entity
data class Pet(

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id val id: PetId = PetId.NULL,
    val name: PetName,
    @field:Enumerated(EnumType.STRING) val kind: PetKind,
    val price: Price,
    val currency: Currency,

)

data class NewPet(
    val name: PetName?,
    val kind: PetKind,
)

data class PetPrice(val price: Price, val currency: Currency)

@JvmInline
value class PetId(val value: Long?) {
    companion object {
        val NULL = PetId(null)
    }
}

@JvmInline
value class PetName(val value: String) {
    init {
        require(value.isNotBlank())
    }
}

@JvmInline
value class Price(val value: Double) {
    init {
        require(value >= 0)
    }
}

@JvmInline
value class Currency(val value: String) {
    init {
        require(value.isNotBlank())
        require(value.length == 3)
    }
}

enum class PetKind {
    DOG, CAT, UNICORN, BIG_FOOT, SNAIL
}
