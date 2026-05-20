package org.devsummit.workshop.kotlin.shop.domain

import org.devsummit.workshop.kotlin.shop.controller.dto.PetDto
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Persistent
import org.springframework.data.relational.core.mapping.Table

@Table("pets")
@Persistent
data class Pet(

    @Id val id: PetId = PetId.NULL,
    val name: PetName,
    val kind: PetKind,
    val price: Price,
    val currency: Currency,
)

data class NewPet(
    val name: PetName?,
    val kind: PetKind,
)

fun Pet.toDto() = PetDto(
    id = this.id.value,
    name = this.name.value,
    kind = this.kind.name,
    price = this.price.value,
    currency = this.currency.value,
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
