# Exercise 3 - Error Handling

## Goal

Replace exception-driven business error handling with typed, explicit functional errors using Arrow `Either`.

## Constraints

- Preserve the same pet-creation behavior and fallback logic
- Represent expected failures as domain error types instead of generic thrown exceptions
- Keep error propagation explicit across `ExternalClient`, `PetService`, and controller layers
- Use Arrow conventions consistently (`right`, `left`, `either {}`, `bind`, `handleErrorWith`)

## Success Criteria

- `ExternalClient` methods return `Either<ErrorType, ValueType>` signatures
- Happy paths return `Right`; expected failures return `Left`
- `PetService` composes async operations with typed error propagation
- Price V2 fallback to V1 is applied only on `Left`
- Controller maps known domain errors to HTTP responses

## 3.2 Add `Either` for Functional Error Handling

Happy paths are easy to read, but production code also needs explicit failure paths.

This step focuses on these questions:

- How do we make expected errors visible in function signatures?
- How do we avoid generic `throw Exception` patterns?
- How do we model business errors without always paying exception costs?

Kotlin provides `Result<T>`, and Arrow provides `Either<LEFT, RIGHT>`.
With `Either`, success is `Right`, and failure is `Left`.

```kotlin
fun buySomething(): Either<CanBuyError, Something> {
    return try {
        externalStoreService.buy().right()
    } catch (e: Exception) {
        CantBuyError(e).left()
    }
}
```

Combined with sealed types, this gives strongly typed error flows.

## Tasks

### 1) Update `ExternalClient` to Return `Either`

- Wrap successful values with `.right()`
- Replace thrown exceptions with error values wrapped in `.left()`

Expected signatures:

```kotlin
suspend fun fetchRandomName(kind: PetKind): Either<PetNameError, PetName> = try {

suspend fun fetchPetPrice(kind: PetKind): Either<PriceV1UnhandledError, PetPrice> = try {

suspend fun fetchPetPriceV2(kind: PetKind): Either<PriceV2Error, PetPrice?> = try {
```

### 2) Update `PetService` Flow

Align generated name type with `Either`:

```kotlin
val generatedNameDeferred = async {
    newPet.name?.right() ?: externalClient.fetchRandomName(newPet.kind)
}
```

Add fallback for price with `.handleErrorWith` (fallback only on `Left`):

```kotlin
val priceDeferred = async {
    externalClient.fetchPetPriceV2(newPet.kind)
        .handleErrorWith {
            externalClient.fetchPetPrice(newPet.kind)
        }
}
```

### 3) Use Arrow `either {}` DSL

Use `bind()` to unwrap `Right` values and short-circuit on `Left`:

```kotlin
either {
    val name: PetName = generatedNameDeferred.await().bind()
    val price = priceDeferred.await().bind()
    val finalPet = Pet(
        name = name,
        kind = newPet.kind,
        price = price.price,
        currency = price.currency,
    )
    repository.save(finalPet)
}
```

## Model Error Families with Sealed Types

Group allowed error types behind one parent interface so returnable errors are compile-time constrained.

```kotlin
sealed class Error(val message: String)
sealed interface PetCreationError

sealed interface PriceV1Error : PetCreationError
sealed interface PetNameError : PetCreationError
```

## Controller Error Mapping

In the controller, map known domain errors to HTTP responses, and optionally rethrow unhandled technical errors to controller advice.

```kotlin
suspend fun createPet(@RequestBody pet: NewPetDto): ResponseEntity<PetDto> {
    val newPet = NewPet(pet.name?.let { PetName(it) }, PetKind.valueOf(pet.kind))
    return service.createPet(newPet).fold(
        {
            when (it) {
                is PetNameTimeout -> ResponseEntity.status(504).build()
                is PetNameUnhandledError -> ResponseEntity.internalServerError().build()
                is PriceV1UnhandledError -> throw it.exception
            }
        },
        { ResponseEntity.ok(it.toDto()) },
    )
}
```

