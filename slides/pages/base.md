---
layout: center
---

# Just syntactic sugar?

![pouring_sugar.gif](/pouring_sugar.gif)

---
layout: cover
---

# Syntax basics

<!-- 
Speaker: THIBAULT

Timing: 15 minutes

-->

---
layout: default

transition: fade
---

# Syntax basics

````md magic-move
```kotlin
fun hello(): String {
}
```

```kotlin
fun hello(who: String?): String {


}
```

```kotlin
fun hello(who: String?): String {

    return "Hello, $who!"
}
```

```kotlin {all|2}
fun hello(who: String?): String {
    val upper: String = who.uppercase()
    return "Hello, $upper!"
}
```

```kotlin {2}
fun hello(who: String?): String {
🚫  val upper: String = who.uppercase()
    return "Hello, $upper!"
}
```

```kotlin {2}
fun hello(who: String?): String {
🚫  val upper: String = who?.uppercase()
    return "Hello, $upper!"
}
```

```kotlin {2}
fun hello(who: String?): String {
    val upper: String = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}
```

```kotlin
fun hello(who: String?): String {
    val upper = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}
```

```kotlin
fun hello(who: String?): String {
    val upper = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}

fun hello() = hello("world")
```
````

<!--
- Types come after, less important than the name of things
- Top level function without class

- Types after, even for parameters
- Null safety: if it can be null, it's declared

- String interpolation

- Elvis operator for null checks

- Null safety call

- Single expression function
-->

---
transition: fade
---

# Syntax basics

````md magic-move
```kotlin
fun hello(who: String?): String {
    val upper = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}

fun hello() = hello("world")
```

```kotlin
fun hello(who: String? = "world"): String {
    val upper = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}

// fun hello() = hello("world")
```
```kotlin
fun hello(who: String? = "world"): String {
    val upper = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}

fun demo() = hello()
```
```kotlin
fun hello(who: String? = "world"): String {
    val upper = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}

fun demo() = hello("TDD")
```
```kotlin
fun hello(who: String? = "world", times: Int = 0,): String {
    val upper = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}

fun demo() = hello("TDD")
```
```kotlin
fun hello(who: String? = "world", times: Int = 0,): String {
    val upper = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}

fun demo() = hello(times = 2)
```
```kotlin
fun hello(who: String? = "world", times: Int = 0,): String {
    val upper = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}

fun demo() = hello("TDD") // return type:String

class Hello(
)
```
```kotlin
fun hello(who: String? = "world", times: Int = 0,): String {
    val upper = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}

fun demo() = hello("TDD") // return type:String

class Hello(
    val who: String,
    val times: Int = 0,
)
```
```kotlin
fun hello(who: String? = "world", times: Int = 0,): String {
    val upper = who?.uppercase() ?: "YOU"
    return "Hello, $upper!"
}

fun demo() = hello("TDD") // return type:String

data class Hello(
    val who: String,
    val times: Int = 0,
)
```
```kotlin

fun hello(who: String? = "world", times: Int = 0,): Hello {
    val upper = who?.uppercase() ?: "YOU"
    return Hello(upper, times)
}

fun demo() = hello("TDD") // return type:Hello

data class Hello(
    val who: String,
    val times: Int = 0,
)
```
````

<!--
- Default parameters avoid creating multiple functions

- Adding a parameter with a default value is not a breaking change
- Int because No primitives, no boxing/unboxing issues
- Trailing comma for easy diff

- Class declared with constructor like a function
- Data class: a mix between Lombok @Data and record

- Update return type transparently with demo(), impacts only where it matters
-->

---

# Syntax basics

````md magic-move
```kotlin
fun hello(who: String? = "world", times: Int = 0,): Hello {
    val upper = who?.uppercase() ?: "YOU"
    return Hello(upper, times)
}

fun demo() = hello("TDD") // return type:Hello

data class Hello(
    val who: String,
    val times: Int = 0,
) {
    fun increment() = this.copy(times = times + 1)
}
```
```kotlin
fun hello(who: String? = "world", times: Int = 0,): Hello {
    val upper = who?.uppercase() ?: "YOU"
    return Hello(upper, times)
}

fun demo() = hello("TDD") // return type:Hello

data class Hello(
    val who: String,
    val times: Int,
) {
    fun increment() = this.copy(times = times + 1)
}

fun Hello.toDto() = HelloDto(who, times)
```

```kotlin
fun hello(who: String? = "world", times: Int = 0,): Hello {
    val upper = who?.uppercase() ?: "YOU"
    return Hello(upper, times)
}

fun demo() = hello("TDD") // return type:Hello

data class Hello(
    val who: String,
    val times: Int,
) {
    fun increment() = this.copy(times = times + 1)
}

fun Hello.toDto() = HelloDto(who, times)
fun String.toHello() = Hello(this)
```
```kotlin
fun hello(who: String? = "world", times: Int = 0,): Hello {
    val upper = who?.uppercase() ?: "YOU"
    return Hello(upper, times)
}

fun demo() = hello("TDD") // return type:Hello

data class Hello(
    val who: String,
    val times: Int,
) {
    fun increment() = this.copy(times = times + 1)
}

fun Hello.toDto() = HelloDto(who, times)
fun String.toHello() = Hello(this)

class Times(val v: Int = 0) {
}
```
```kotlin
fun hello(who: String? = "world", times: Int = 0,): Hello {
    val upper = who?.uppercase() ?: "YOU"
    return Hello(upper, times)
}

fun demo() = hello("TDD") // return type:Hello

data class Hello(
    val who: String,
    val times: Int,
) {
    fun increment() = this.copy(times = times + 1)
}

fun Hello.toDto() = HelloDto(who, times)
fun String.toHello() = Hello(this)

class Times(val v: Int = 0) {
    init {
        require(v >= 0)
    }
}
```
```kotlin
fun hello(who: String? = "world", times: Times = Times(),): Hello {
    val upper = who?.uppercase() ?: "YOU"
    return Hello(upper, times)
}

fun demo() = hello("TDD") // return type:Hello

data class Hello(
    val who: String,
    val times: Times = Times(0),
) {
    fun increment() = this.copy(times = times + 1)
}

fun Hello.toDto() = HelloDto(who, times)
fun String.toHello() = Hello(this)

class Times(val v: Int = 0) {
    init {
        require(v >= 0)
    }
}
```
```kotlin
fun hello(who: String? = "world", times: Times = Times(),): Hello {
    val upper = who?.uppercase() ?: "YOU"
    return Hello(upper, times)
}

fun demo() = hello("TDD") // return type:Hello

data class Hello(
    val who: String,
    val times: Times = Times(0),
) {
🚫   fun increment() = this.copy(times = times + 1)
}

fun Hello.toDto() = HelloDto(who, times)
fun String.toHello() = Hello(this)

class Times(val v: Int = 0) {
    init {
        require(v >= 0)
    }
}
```

```kotlin
fun hello(who: String? = "world", times: Times = Times(),): Hello {
    val upper = who?.uppercase() ?: "YOU"
    return Hello(upper, times)
}

fun demo() = hello("TDD") // return type:Hello

data class Hello(
    val who: String,
    val times: Times = Times(0),
) {
    fun increment() = this.copy(times = times + 1)
}

fun Hello.toDto() = HelloDto(who, times)
fun String.toHello() = Hello(this)

class Times(val v: Int = 0) {
    init {
        require(v >= 0)
    }

    operator fun plus(i: Int) = Times(v + i)
}
```
```kotlin
fun hello(who: String? = "world", times: Times = Times(),): Hello {
    val upper = who?.uppercase() ?: "YOU"
    return Hello(upper, times)
}

fun demo() = hello("TDD") // return type:Hello

data class Hello(
    val who: String,
    val times: Times = Times(0),
) {
    fun increment() = this.copy(times = times + 1)
}

fun Hello.toDto() = HelloDto(who, times)
fun String.toHello() = Hello(this)

@JvmInline
value class Times(val v: Int = 0) {
    init {
        require(v >= 0)
    }

    operator fun plus(i: Int) = Times(v + i)
}
```
````

<!--
- Data class is more than a record; we can easily work with immutability using copy
- Named parameters!

- Also useful to make code more readable left to right
- Extension function: really useful to add behavior

- Strong typing, assert on constructor value

- Operator overloading (only +-*\, not like Scala)

- Value class for strong typing -> no more String, Int... use types with meaning without performance issues
-->

---

# Collection

````md magic-move
```kotlin
fun main() {
}
```
```kotlin
fun main() {
    val list = listOf(1, 2, 3, 4)
}
```
```kotlin
fun main() {
    val list = listOf(1, 2, 3, 4)
    list.map({ it -> it + 1 })
}
```
```kotlin
fun main() {
    val list = listOf(1, 2, 3, 4)
    list.map { it -> it + 1 }
}
```
```kotlin
fun main() {
    val list = listOf(1, 2, 3, 4)
    list.map { it + 1 }
}
```
```kotlin
fun main() {
    val list = listOf(1, 2, 3, 4)
    list.map { it + 1 }
        .filter { it > 2 }
}
```
```kotlin
fun main() {
    val list = listOf(1, 2, 3, 4)
    list.map { it + 1 }
        .filter { it > 2 }
        .onEach { println(it) }
        .first { it % 3 == 0 }
}
```
```kotlin
fun main() {
    val list = listOf(1, 2, 3, 4)
    list.map { it + 1 }
        .filter { it > 2 }
        .onEach { println(it) }
        .first { it % 3 == 0 }
}

// 3 4 5
```
```kotlin
fun main() {
    val list = listOf(1, 2, 3, 4)
    list.asSequence()
        .map { it + 1 }
        .filter { it > 2 }
        .onEach { println(it) }
        .first { it % 3 == 0 }
}
// 3 
```
````

<!--
- Type inference (if you want them, IDE can show them)
- Immutable by default
- Direct access to map & co functions, no need to use stream

- No () if the last parameter is a lambda (DSL easy)
- it by default

- Eagerly evaluated (a new list is created each time)

- Same API for lazy version
-->
