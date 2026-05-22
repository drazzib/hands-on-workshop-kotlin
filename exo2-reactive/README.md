# Exercise #2: Kotlin Reactive Programming with Coroutines

## Goal

Move the service from blocking orchestration to coroutine-based, non-blocking I/O where possible.

## Constraints

- Keep the same business flow while changing execution model
- Prefer coroutine-native APIs (`suspend`, `await*`, `coroutineScope`) over blocking calls
- Isolate unavoidable blocking operations with `withContext(Dispatchers.IO)`
- For full repository reactivity, migrate from JPA/JDBC to R2DBC as described

## Success Criteria

- Service and repository methods are coroutine-based (`suspend`)
- WebClient integration uses `await*` instead of `.block()`
- Database access is migrated to R2DBC (`CoroutineCrudRepository` and R2DBC config)
- Name and price lookups execute concurrently when creating pets
- Price V2 failures fall back to V1

## 2.1 Convert Service Methods to `suspend`

This service mostly coordinates HTTP and database I/O, which is a good fit for coroutines.

- Use `suspend` on methods that perform asynchronous waiting
- Keep Kotlin return types (`Pet`, `List<Pet>`, etc.); do not switch to `Mono`/`Flux`
- Use `delay(...)` instead of `Thread.sleep(...)` when you need a simulated wait

```kotlin
suspend fun mySuspendableFunction(): String {
    delay(1000)
    return "Hello, World!"
}
```

### Task

- Add `suspend` to all methods in `PetController` and `PetService`
- For still-blocking calls in `PetService`, isolate them with `withContext(Dispatchers.IO) { ... }`

## 2.2 Make `fetchRandomName` Non-Blocking

`fetchRandomName` already uses `WebClient`, but `.block()` makes it blocking.

### Task

- Replace `bodyToMono<RemotePetNameDto>().block()` with coroutine bridge methods such as `.awaitBody<RemotePetNameDto>()` or `.awaitBodyOrNull<RemotePetNameDto>()`
- Mark the method `suspend`
- Remove the `Dispatchers.IO` wrapper for this method once fully non-blocking

### Bonus

If the type can be deduced, you can use the reified version without specifying the type parameter:

```kotlin
val dto: RemotePetNameDto = webClient.get()
    /* ... */
    .awaitBody()
```

## 2.3 Make `fetchPetPrice` Non-Blocking

### Task

- Apply the same refactor as 2.2 to `fetchPetPrice`

## 2.4 Make Repository Reactive (JPA -> R2DBC)

To be fully reactive on the DB side, replace JDBC/JPA with R2DBC.

### `pom.xml` updates

Remove JPA dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

Remove Kotlin JPA compiler plugin configuration:

```xml
<compilerPlugins>
    <plugin>jpa</plugin>
</compilerPlugins>
<pluginOptions>
    <option>all-open:annotation=jakarta.persistence.Entity</option>
    <option>all-open:annotation=jakarta.persistence.MappedSuperclass</option>
    <option>all-open:annotation=jakarta.persistence.Embeddable</option>
</pluginOptions>
```

Add R2DBC dependencies:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-r2dbc</artifactId>
</dependency>

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>r2dbc-postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Code updates

- In `PetRepository`, replace `JpaRepository` with `CoroutineCrudRepository`
- In `Pet.kt`, replace JPA annotations with Spring Data relational annotations:

```kotlin
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
```

- In `application.yml`, replace JDBC URL:

```yml
  datasource:
    url: jdbc:postgresql://localhost:5432/devsummit
```

with R2DBC URL:

```yml
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/devsummit
```

- In `PetService`, remove all the `withContext(Dispatchers.IO) { ... }` wrappers around repository calls, as R2DBC is non-blocking and does not require an IO dispatcher.

## 2.5 Run External Calls in Parallel

Name generation and price lookup are independent and can run concurrently.

### Hint

- Use `async { ... }` when you need a result
- Use `launch { ... }` for fire-and-forget jobs

```kotlin
fun startWork(): TaskForToday = coroutineScope {
    val deferredIdea = async { thinkToAnIdea() }
    launch { sitAtYourDesk() }
    val badIdea = deferredIdea.await()
    TaskForToday(badIdea)
}
```

### Task

- In `PetService` wrap both calls to externalClient as separate async tasks
- Use `coroutineScope` so both child coroutines are structured and awaited

## 2.6 Add Fallback to `fetchPetPriceV2`

`externalClient.fetchPetPriceV2(newPet.kind)` exists but may fail.

### Task

- Inside the async, try price V2 first, on error, fallback to V1

### Hint

Use regular `try/catch` around coroutine calls when needed:

```kotlin
val result = try {
    val a = callACoroutine1()
    callACoroutine2(a)
} catch (e: Exception) {
    log.error(e) { "No..." }
    "fallbackValue"
}
```


## Next Step

Continue to [Exercise #3: Kotlin advanced](../exo3-advanced/README.md)
