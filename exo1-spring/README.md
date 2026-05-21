# Exercise #1: Spring and Kotlin 

## Goal

Build a Kotlin-first domain model for the pet API by applying core language features (types, enum, extension functions, null safety, constructor injection, and value classes).

## Constraints

- Keep the same functional behavior of the existing API flow
- Follow Kotlin idioms introduced in this exercise (`data class`, `enum class`, nullable types, value classes)
- Update related mappings and signatures consistently (`Pet`, `PetDto`, `NewPet`, `NewPetDto`, and service methods)

## Success Criteria

- Domain and DTO models use explicit, correct Kotlin types
- `kind` uses `PetKind` enum instead of raw `String`
- `PetService` uses constructor injection with `private val` dependencies
- Missing pet names are handled through null-safe fallback to `externalClient.fetchRandomName()`
- Domain primitives are replaced with value classes (`PetName`, `Price`, `Currency`, `PetId`)

## Quick Hints

- Kotlin types are declared after the name: `val name: MyType`
- Nullable types use `?`: `val id: Long?`
- Use `data class` instead of Lombok `@Data`
- Trailing commas are valid in Kotlin
- `TODO()` compiles and is useful for unfinished code

## 1.0 Define Core Types

The Pet API model must expose:

- `id`: nullable `Long`
- `name`: `String`
- `kind`: `String`
- `price`: `Double`
- `currency`: `String`

### Task

- Add missing type declarations in `Pet.kt`
- Fix mapping between `Pet` and `PetDto`

## 1.1 Replace `kind` String With Enum

`kind` is limited to known values.

```java
public enum PetKind {
    DOG, CAT, UNICORN, BIG_FOOT, SNAIL
}
```

### Task

- Convert this enum to Kotlin in `Pet.kt` (if you copy-paste the java, IntelliJ should do this automatically)
- Use the enum in `Pet` and `PetDto` instead of `String`

## 1.2 Add an Extension Function

Kotlin extension function syntax:

```kotlin
fun TheType.theFunction() = "something"
```
### Task

PetDto has leak into our domain Pet for the mapping. 
Remove the function in `Pet` and add an extension function in `PetDto.kt` to convert `Pet` to `PetDto`:

```kotlin
fun Pet.toDto() = PetDto(
    id = this.id,
    name = this.name,
    kind = this.kind,
    price = this.price,
    currency = this.currency,
)
```

## 1.3 Use Constructor Injection

`PetService` already depends on `PetRepository`

### Task

We will need to use `ExternalClient` in the next steps, so let's prepare for that by adding it:

- Inject dependencies through the constructor
- Use `private val` so injected dependencies are private class properties
- Add `private val externalClient: ExternalClient` to `PetService`

## 1.4 Apply Null Safety

Kotlin null-safety tools:

- Nullable type: `Type?`
- Safe call: `?.`
- Fallback value: `?:` (Elvis operator)

```kotlin
val myNull: String? = null
myNull?.length ?: 0
```

Sometimes a pet has no name, and a generator must provide one.

### Task

- Make `name` nullable in `NewPetDto` and `NewPet`
- In `PetService`, if `name` is null, call `externalClient.fetchRandomName()`

## 1.5 Use External Price Service

### Task

- Replace hard-coded pet price in `PetService`
- Call `externalClient.fetchPetPrice(kind: PetKind)` instead

## 1.6 Introduce Value Classes

Value classes reduce mix-ups between similar primitives.

```kotlin
fun user(firstName: FirstName, lastName: LastName, nickname: NickName)

@JvmInline
value class FirstName(val value: String) {
    init {
        require(value.isNotBlank())
    }
}
```

`@JvmInline` is required until JVM value objects are natively supported.
Use `init` to enforce domain validation rules.

### Task

We have several primitive types in our model that can be replaced with value classes for better type safety and domain expressiveness.

Replace primitive types with value classes:

- `PetName` in `Pet`, `NewPet`, and return type of `fetchRandomName`
- Do the same for `Price`, `Currency`, and `PetId`


## Next Step

Continue to [Exercise #2: Kotlin reactive programming](../exo2-reactive/README.md)
