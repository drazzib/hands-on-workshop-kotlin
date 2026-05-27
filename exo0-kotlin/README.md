# Exo 0 — Kotlin Syntax Crash Course

> **Goal:** Learn the essential Kotlin syntax in 10 steps — all in a single file.
> **Time:** ~20 minutes

## How to work

1. Open [`Playground.kt`](src/main/kotlin/org/devsummit/workshop/kotlin/exo0/Playground.kt) — **all your work goes here** (it starts empty!)
2. Follow the steps below and write the code directly in `Playground.kt`
3. After each step, uncomment the matching test block in [`PlaygroundTest.kt`](src/test/kotlin/org/devsummit/workshop/kotlin/exo0/PlaygroundTest.kt)
4. All 7 tests green? 🎉 You've mastered the basics!

> 💡 **Stuck?** Check [`Solution.kt`](src/main/kotlin/org/devsummit/workshop/kotlin/exo0/solution/Solution.kt) for the full reference implementation.

> **Steps 1–3** are verified by running `main()` in your IDE and reading the console output.
> **Steps 4–10** each have a test block to uncomment in `PlaygroundTest.kt`.

---

## Step 1 — Declare a function and create `main`

### Functions in Kotlin

Every function is declared with the `fun` keyword:

```kotlin
fun greet(): Unit {
    println("Hello!")
}
```

- `fun` — keyword to declare a function
- `greet` — the function name
- `()` — parameter list (empty here)
- `: Unit` — return type

> 💡 **`Unit`** is Kotlin's equivalent of Java's `void` — it means "no meaningful return value".
> The `: Unit` return type is **optional** and almost always omitted:
> ```kotlin
> fun greet() {   // identical — Unit is implicit
>     println("Hello!")
> }
> ```

> 💡 **Everything is `public` by default** in Kotlin. No need to write `public fun greet()`.

### The entry point

`main` is just a regular **top-level function** — no class, no `static`, no `public`, and **no `String[] args`** needed:

```kotlin
fun main() {
    // your code goes here
}
```

✏️ **Task:** Write a `fun main()` in `Playground.kt` that prints `"Hello, Kotlin!"`.

> 🤩 **Why it's cool?** In Java you need a class with a `public static void main(String[] args)` just to run code. In Kotlin, `main` is a plain top-level function — no wrapper class, no boilerplate. No more utility classes!

---

## Step 2 — Variables

Declare a mutable variable with `var`:

```kotlin
var name = "Company"   // type String is inferred automatically
```

> 💡 `var` = **mutable** — you can reassign the variable later.
> `val` = **immutable** (like Java's `final`) — can only be assigned once.

✏️ **Task:**
1. Declare `var name = "YourName"` inside `main()`
2. Replace your `println` with `println("Hello " + name)`
3. Your IDE should show a warning because `name` is never updated — replace `var` with `val`.

> 🤩 **Why it's cool?** Kotlin is **immutable by default** — `val` is the natural choice and `var` requires you to explicitly opt in to mutability. This pushes you toward safer, more predictable code.

---

## Step 3 — String templates

Instead of concatenating with `+`, Kotlin lets you embed variables and expressions directly in strings.

```kotlin
val name = "Company"
println("Hello, $name!")                   // → Hello, Company!
println("Hello, ${name.uppercase()}!")     // → Hello, COMPANY!
```

- `$name` — inlines a variable
- `${expr}` — inlines any expression (method call, arithmetic, …)

✏️ **Task:** Replace `println("Hello " + name)` with `println("Hello, ${name.uppercase()}!")`.

> 🤩 **Why it's cool?** String templates are simpler and far more readable than concatenation or `String.format()`. No more `"Hello, " + name + "!"`.

---

## Step 4 — Functions with parameters and a return type

A function can receive parameters and return a value.

**Block body** (with explicit `return`):

```kotlin
fun double(n: Int): Int {
    return n * 2
}
```

**Expression body** (single expression, `return` replaced by `=`):

```kotlin
fun double(n: Int): Int = n * 2
```

**Expression body with inferred return type** (the compiler deduces it):

```kotlin
fun double(n: Int) = n * 2
```

> 💡 When using an expression body, the compiler infers the return type from the expression — you can omit it for brevity.

✏️ **Task:** Declare a `format` function and call it in `main`:

```kotlin
fun format(s: String): String {
    return "Hello ${s.uppercase()}"
}

// in main:
println(format("DevSummit"))   // → Hello DEVSUMMIT
```

> ✂️ Uncomment the **Step 4** test in `PlaygroundTest.kt` to validate.

> 🤩 **Why it's cool?** Less boilerplate to update during refactoring — but you still get a **compilation error** if you make a breaking change to the return type. Best of both worlds.

---

## Step 5 — Nullable types

In Kotlin, a type is **non-nullable by default** — the compiler prevents `null` from being assigned to a `String`.
Add `?` to allow `null`:

```kotlin
val a: String  = "hello"   // cannot be null
val b: String? = null      // nullable — ok
```

The **safe-call operator** `?.` calls a method only if the receiver is not null, and returns `null` otherwise:

```kotlin
val b: String? = null
println(b?.uppercase())   // → null  (no crash)
```

✏️ **Task:** Update `format` to accept a nullable `String?` and use `?.`:

```kotlin
fun format(s: String?): String = "Hello ${s?.uppercase()}"
```

Call `format(null)` in `main` and observe the output.

> ⚠️ Notice anything wrong? When `s` is `null`, `s?.uppercase()` returns `null` too,
> so you get `"Hello null"`. Step 6 will fix this.

> 🤩 **Why it's cool?** Null-safety means you never have to wonder *"can this value be null?"* — the type system tells you at **compile time**. No more `@Nullable` / `@NonNull` annotations that are just hints and ignored by the compiler.

---

## Step 6 — `if` as an expression

In Kotlin, `if/else` **returns a value** — there is no ternary operator (`? :`).

```kotlin
val label = if (score > 0) "positive" else "non-positive"
```

✏️ **Task:** Rewrite `format` to properly handle `null` using an `if` expression:

```kotlin
fun format(s: String?): String {
    return if (s == null) {
        "Hello DevSummit!"
    } else {
        "Hello ${s.uppercase()}"
    }
}
```

> ✂️ Uncomment the **Step 6** test in `PlaygroundTest.kt` to validate.

> 💡 **Smart cast** — did you notice that inside the `else` branch, `s` is no longer `String?`?
> After the `if (s == null)` check the compiler **knows** `s` cannot be `null` in the `else`,
> so it automatically treats it as `String`. No cast needed — this is called a **smart cast**.

> 🤩 **Why it's cool?** Using `if` as an expression encourages a **single exit point** — the whole function becomes one expression. Smart cast means you don't need to rename or re-declare a variable just to use it as a non-nullable type.

---

## Step 7 — `when` expression

`when` replaces `switch` and can return a value.

```kotlin
val sound = when (wind) {
    "Zephyr"  -> "shhh-wshhh"
    "Tornado"  -> "SCREEECH-CHUUUGGG-RUMMMBLE"
    else   -> "what?"
}
```

✏️ **Task:** Implement `describeSpecies(species: String): String`.

```kotlin
// expected: "cat" → "meow" | "dog" → "woof" | anything else → "..."
fun describeSpecies(species: String): String = when (species) { ... }
```

> ✂️ Uncomment the **Step 7** test in `PlaygroundTest.kt` to validate.

> 🤩 **Why it's cool?** Java finally got switch expressions in Java 14 — Kotlin has had `when` from day one, and it's more powerful: no fall-through, works as an expression everywhere, and exhaustiveness checking on sealed types.

---

## Step 8 — Class

A Kotlin class declares its properties directly in the **primary constructor**.
No need for a separate field declaration and assignment.

```kotlin
class Pony(val name: String, val kind: String) {
    fun fly(): Boolean = if (kind == "pegasus") true else false
}
```

✏️ **Task:** Declare `Animal` with name and species as property. Add a `speak()` method that returns the species sound.

> ✂️ Uncomment the **Step 8** test in `PlaygroundTest.kt` to
validate.

> 🤩 **Why it's cool?** Less boilerplate — constructor parameters double as properties. The syntax reads like a function call, making the class declaration concise and intuitive.

---

## Step 9 — Data class

Adding the `data` keyword to a class auto-generates:
- `equals()` / `hashCode()` — structural comparison
- `toString()` — human-readable output
- `copy()` — create a modified copy

```kotlin
data class Pet(val name: String, val age: Int)

val p1 = Pet("Luna", 3)
val p2 = Pet("Luna", 3)
println(p1 == p2)           // true  (structural equality)
println(p1)                 // Pet(name=Luna, age=3)
```

### Named parameters and `copy()`

Kotlin supports **named parameters** — you can pass arguments by name instead of position:

```kotlin
val p1 = Pet(name = "Luna", age = 3)
```

This is especially useful with `copy()`, which lets you create a modified copy by naming only the fields you want to change:

```kotlin
val older = p1.copy(age = 4)   // creates Pet("Luna", 4)
```

✏️ **Task:** Declare `data class Pet(val name: String, val age: Int)`.

> ✂️ Uncomment the **Step 9** test in `PlaygroundTest.kt` to validate.

> 🤩 **Why it's cool?** Data classes are like Java records, with `copy()` and named parameters giving you the right tooling to work with immutable objects. Structural equality and readable `toString()` come for free.

---

## Step 10 — Extension functions

Add new functions to **existing types** without modifying their source or using inheritance.

```kotlin
fun Int.double(): Int = this * 2

fun Any.asString(): String = this.toString().uppercase()

// usage
```

✏️ **Task:** Implement both extension functions.

```kotlin
fun String.shout(): String = /** return the string as uppercase with `!` at the end */
fun Animal.describe(): String = /** return a string with `species named name` like "dog named Rex" */
```

> ✂️ Uncomment the **Step 10** test in `PlaygroundTest.kt` to validate.

> 🤩 **Why it's cool?** You can add behavior to a type without touching its source code or using inheritance. Perfect for mapping: instead of `asDto(domain: MyDomainObject)` (right-to-left reading), write `fun MyDomainObject.asDto()` and call `domain.asDto()` — natural left-to-right reading, just like a regular method.

---

## Quick reference

```kotlin
// Variables
val x = 42           // immutable (val = value)
var y = 0            // mutable   (var = variable)

// String template
"Hello, $name! ${name.uppercase()}"

// Function
fun double(n: Int): Int = n * 2   // explicit return type
fun double(n: Int) = n * 2        // inferred return type

// Null safety
val s: String? = null
s?.uppercase()        // null if s is null
s ?: "default"        // "default" if s is null

// if expression
val r = if (x > 0) "pos" else "non-pos"

// when expression
when (x) { 1 -> "one"; 2 -> "two"; else -> "other" }

// Class
class Animal(val name: String) { fun greet() = "Hi, $name" }

// Data class with named parameters
data class Pet(val name: String, val age: Int)
val p = Pet(name = "Luna", age = 3)
val older = p.copy(age = 4)

// Extension function
fun String.shout() = uppercase() + "!"
```
