package org.devsummit.workshop.kotlin.shop.domain

import jakarta.persistence.*

@Table(name = "pets")
@Entity
data class Pet(

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id val id: Long? = null,
    val name: String,
    val kind: String,
    val price: Double,
    val currency: String,

)

data class NewPet(
    val name: String,
    val kind: String,
)