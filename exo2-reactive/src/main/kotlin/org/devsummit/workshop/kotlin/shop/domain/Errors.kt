package org.devsummit.workshop.kotlin.shop.domain

sealed class Error(override val message: String): Exception(message)

sealed interface PriceV1Error
class PriceV1UnhandledError(val exception: Exception) : Error(exception.message ?: ""), PriceV1Error

sealed interface PetNameError
class PetNameTimeout(kind: PetKind) : Error("Timeout when calling for $kind"), PetNameError
class PetNameUnhandledError(val exception: Exception) : Error(exception.message ?: ""), PetNameError
