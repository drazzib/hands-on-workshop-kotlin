package org.devsummit.workshop.kotlin.exo0.solution

// ─── Steps 1 + 2 + 3 + 4 ───────────────────────────────────────────────────

fun main() {
    println("Hello, Kotlin!")                    // Step 2
    var name = "Company"                       // Step 3 — mutable variable
    println("Hello " + name)                     // Step 3 — string concatenation
    println("Hello, ${name.uppercase()}!")       // Step 4 — string template + expression
    println(format("DevSummit"))             // Step 5 — function call
    println(format(null))                    // Step 7 — null handled
}

// ─── Step 5 — Function with parameter and return type ───────────────────────

// Step 5 initial version (non-nullable):
// fun format(s: String): String = "Hello ${s.uppercase()}"

// Step 6 intermediate (nullable, but "Hello null" problem):
// fun format(s: String?): String = "Hello ${s?.uppercase()}"

// Step 7 final version — if as expression + smart cast:
fun format(s: String?): String {
    return if (s == null) {
        "Hello DevSummit!"
    } else {
        "Hello ${s.uppercase()}"   // s is smart-cast to String here
    }
}

// ─── Step 8 — when expression ───────────────────────────────────────────────

fun describeSpecies(species: String): String = when (species) {
    "cat"  -> "meow"
    "dog"  -> "woof"
    else   -> "..."
}

// ─── Step 9 — Class ─────────────────────────────────────────────────────────

class Animal(val name: String, val species: String) {
    fun speak(): String = describeSpecies(species)
}

// ─── Step 10 — Data class ────────────────────────────────────────────────────

data class Pet(val name: String, val age: Int)

// ─── Step 11 — Local function ────────────────────────────────────────────────

fun greetAll(names: List<String>): List<String> {
    fun greet(name: String) = "Hello, $name!"
    return names.map { greet(it) }
}

// ─── Step 12 — Extension functions ──────────────────────────────────────────

fun String.shout(): String = uppercase() + "!"

fun Animal.describe(): String = "$species named $name"
