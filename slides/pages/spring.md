---
layout: cover
---
# Spring integration

<!-- 
Speaker: DAMIEN

Timing : 8 minutes

-->

---

# Spring integration

- Spring documentation has 100% of code snippets in Kotlin

- Spring contains multiple Kotlin extensions and custom DSLs

- Kotlin coroutines are fully integrated with WebFlux

<!--
Speaker: Damien

Let's dive into some of those extensions / DSL / integration.
-->

---
layout: two-columns
---

::title::
# MockMvc DSL

::left::
<div v-click.at="1">
<i>MockMvc</i> with Java:
```java
mockMvc.perform(
  post("/mockmvc/validate")
    .accept(APPLICATION_JSON)
    .contentType(APPLICATION_JSON)
    .content(mapper.writeValueAsString(Something()))
    .andExpect(status().isOk)
    .andExpect(content().contentType(APPLICATION_JSON))
    .andExpect(content().string("{}"))
}
```
</div>

::right::
<div v-click.at="2">
<i>MockMvc</i> with Kotlin DSL:
```kotlin
mockMvc.post("/mockmvc/validate") {
  contentType = APPLICATION_JSON
  content = mapper.writeValueAsString(Something())
  accept = APPLICATION_JSON
}.andExpect {
    status { isOk }
    content { contentType(APPLICATION_JSON) }
    content { json("{}") }
}
```
</div>

<!--
Speaker: DAMIEN

MockMvc Kotlin DSL is a Kotlin extension provided by Spring.

MockMvc Kotlin DSL can be used to make our test code cleaner (more concise and less error-prone).
-->

---
layout: two-columns
---

::title::
# Spring Security DSL

::left::

<div v-click.at="1">
Spring Security with Java:
```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) {
  return http
    .csrf(AbstractHttpConfigurer::disable)
    .authorizeRequests()
    .mvcMatchers(urlsWithAnonymousAccess).permitAll()
    .anyRequest().fullyAuthenticated()
    .and()
    .oauth2ResourceServer(oauth2 -> {
      oauth2.jwt(jwt -> {
        jwt.decoder(jwtDecoder);
        jwt.jwtAuthenticationConverter(jwtConverter::apply);
      });
    }).build();
}
```
</div>

::right::

<div v-click.at="2">
Spring Security with Kotlin DSL:
```kotlin
@Bean
fun filterChain(http: HttpSecurity): SecurityFilterChain =
  http.csrf { it.disable() }
    .authorizeHttpRequests {
        authorize("/secured/user", hasRole("USER"))            
        authorize("/secured/admin", hasRole("ADMIN"))          
        authorize(anyRequest, authenticated)                   
    }
    .oauth2ResourceServer { oauth ->
      oauth.jwt { jwt ->
        jwt.decoder(jwtDecoder)
          .jwtAuthenticationConverter { convert(it) }
      }
    }
    .build()
```
</div>


<!--
Speaker: DAMIEN

Like MockMvc, Spring Security provide a Kotlin extension.

Spring Security Kotlin DSL can be used to make our security config less error-prone).
-->
---

# Kotlin Coroutines

<v-clicks>

- Kotlin Coroutines is a library to write asynchronous, non-blocking code
- Spring WebFlux (_Controller_) have extensions to support Coroutines
- Same apply for R2DBC (_Repository_) for reactive database access

</v-clicks>

<!--
Speaker: Damien

Kotlin uses asynchronous programming built around coroutines,
which let you write asynchronous code in a natural, sequential style using suspending functions.
Coroutines are lightweight alternatives to threads.
They can suspend without blocking system resources and are resource-friendly,
making them better suited for fine-grained concurrency.
-->
----

# Kotlin Coroutines

How to migrate to Kotlin Coroutines?

````md magic-move
```kotlin
interface ProductRepository : CrudRepository<Product, Long>

interface StockRepository : CrudRepository<Stock, Long>

fun getProductAndStock(id: ProductId): ProductStockDTO {
    val product = productRepository.findById(id.id)
    val stock = stockRepository.findById(id.id)
    product?.let { ProductStockDTO(it, stock?.quantity ?: 0) }
        ?: throw Exception("Product not found!")
}
```
```kotlin
interface ProductRepository : CoroutineCrudRepository<Product, Long>

interface StockRepository : CoroutineCrudRepository<Stock, Long>

suspend fun getProductAndStock(id: ProductId): ProductStockDTO {
    val product = productRepository.findById(id.id)
    val stock = stockRepository.findById(id.id)
    product?.let { ProductStockDTO(it, stock?.quantity ?: 0) }
        ?: throw Exception("Product not found!")
}
```
```kotlin
interface ProductRepository : CoroutineCrudRepository<Product, Long>

interface StockRepository : CoroutineCrudRepository<Stock, Long>

suspend fun getProductAndStock(id: ProductId): ProductStockDTO = coroutineScope {
    val product = async { productRepository.findById(id.id) }
    val stock = async { stockRepository.findById(id.id) }
    product?.let { ProductStockDTO(it, stock?.quantity ?: 0) }
        ?: throw Exception("Product not found!")
}
```
```kotlin
interface ProductRepository : CoroutineCrudRepository<Product, Long>

interface StockRepository : CoroutineCrudRepository<Stock, Long>

suspend fun getProductAndStock(id: ProductId): ProductStockDTO = coroutineScope {
    val product = async { productRepository.findById(id.id) }
    val stock = async { stockRepository.findById(id.id) }
    product.await()?.let { ProductStockDTO(it, stock.await()?.quantity ?: 0) }
        ?: throw Exception("Product not found!")
}
```
````
<!--
Speaker: Damien

Here is an example of migrating a blocking code to a non-blocking code with Kotlin Coroutines.

Step #1 The most basic building block of coroutines is the suspending function.
It allows a running operation to pause and resume later without affecting the structure of your code.
You can only call a suspending function from another suspending function

Step #2 The async() coroutine builder function starts a concurrent computation inside an existing coroutine scope
and returns a Deferred handle that represents an eventual result

Step #3 Use the .await() function to suspend the code until the result is ready:
-->
