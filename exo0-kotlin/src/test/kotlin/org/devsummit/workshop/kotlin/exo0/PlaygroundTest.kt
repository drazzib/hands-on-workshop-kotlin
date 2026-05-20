package org.devsummit.workshop.kotlin.exo0

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

/**
 * Auto-validates each exercise step.
 * Steps 1–3 are verified by running main() and reading the console output.
 * Steps 4–10: uncomment each block once you've implemented the step, then run:
 *
 *   mvn test -pl exo0-kotlin   (from the code/ directory)
 */
class PlaygroundTest {

    // ✂️ Uncomment when you've completed Step 4 — format function:
    @Test
    fun `step 4 - format non-null`() {
        assertThat(format("DevSummit")).isEqualTo("Hello DEVSUMMIT")
        assertThat(format("kotlin")).isEqualTo("Hello KOTLIN")
    }

    // ✂️ Uncomment when you've completed Step 6 — if expression + null handling:
    @Test
    fun `step 6 - format null`() {
        assertThat(format(null)).isEqualTo("Hello DevSummit!")
        assertThat(format("Kotlin")).isEqualTo("Hello KOTLIN")
    }

    // ✂️ Uncomment when you've completed Step 7 — when expression:
    @Test
    fun `step 7 - when expression`() {
        assertThat(describeSpecies("cat")).isEqualTo("meow")
        assertThat(describeSpecies("dog")).isEqualTo("woof")
        assertThat(describeSpecies("fish")).isEqualTo("...")
    }

    // ✂️ Uncomment when you've completed Step 8 — class:
    @Test
    fun `step 8 - class`() {
        val animal = Animal("Rex", "dog")
        assertThat(animal.name).isEqualTo("Rex")
        assertThat(animal.species).isEqualTo("dog")
        assertThat(animal.speak()).isEqualTo("woof")
    }

    // ✂️ Uncomment when you've completed Step 9 — data class:
    @Test
    fun `step 9 - data class`() {
        val pet1 = Pet("Luna", 3)
        val pet2 = Pet("Luna", 3)
        // data class → structural equality (without `data` this test fails)
        assertThat(pet1).isEqualTo(pet2)
        // data class → meaningful toString containing field values
        assertThat(pet1.toString()).contains("Luna")
        assertThat(pet1.toString()).contains("3")
    }

    // ✂️ Uncomment when you've completed Step 10 — extension functions:
    @Test
    fun `step 10 - extension function shout`() {
        assertThat("kotlin".shout()).isEqualTo("KOTLIN!")
        assertThat("hello world".shout()).isEqualTo("HELLO WORLD!")
    }

    @Test
    fun `step 10 - extension function describe`() {
        assertThat(Animal("Rex", "dog").describe()).isEqualTo("dog named Rex")
        assertThat(Animal("Whiskers", "cat").describe()).isEqualTo("cat named Whiskers")
    }
}
