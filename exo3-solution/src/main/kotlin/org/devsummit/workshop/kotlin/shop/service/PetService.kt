package org.devsummit.workshop.kotlin.shop.service

import arrow.core.Either
import arrow.core.handleErrorWith
import arrow.core.raise.context.bind
import arrow.core.raise.context.either
import arrow.core.right
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.devsummit.workshop.kotlin.shop.domain.NewPet
import org.devsummit.workshop.kotlin.shop.domain.Pet
import org.devsummit.workshop.kotlin.shop.domain.PetCreationError
import org.devsummit.workshop.kotlin.shop.domain.PetKind
import org.devsummit.workshop.kotlin.shop.domain.PetPrice
import org.devsummit.workshop.kotlin.shop.domain.PriceV1Error
import org.devsummit.workshop.kotlin.shop.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class PetService(
    private val repository: PetRepository,
    private val externalClient: ExternalClient
) {
    private val logger = KotlinLogging.logger {}
    suspend fun findAll() =
        repository.findAll()

    suspend fun createPet(newPet: NewPet): Either<PetCreationError, Pet> = either {
        coroutineScope {
            val petName = async { newPet.name?.right() ?: externalClient.fetchRandomName(newPet.kind) }
            val petPrice = async { fetchPetPrice(newPet.kind) }.await().bind()
            val pet = Pet(name = petName.await().bind(), kind = newPet.kind, price = petPrice.price, currency = petPrice.currency)
            repository.save(pet)
        }
    }

    suspend fun fetchPetPrice(kind: PetKind): Either<PriceV1Error, PetPrice> {
        return externalClient.fetchPetPriceV2(kind)
            .handleErrorWith {
                externalClient.fetchPetPrice(kind)
            }
    }
}