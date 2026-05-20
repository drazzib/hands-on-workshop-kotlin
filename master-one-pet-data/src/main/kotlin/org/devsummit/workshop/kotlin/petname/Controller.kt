package org.devsummit.workshop.kotlin.petname

import kotlinx.coroutines.delay
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class Controller {
    @GetMapping("/api/v1/pet-name")
    suspend fun getPetName(@RequestParam kind: String) : ResponseEntity<Any> {
        delay(Random.nextLong(100, 3000))
        return when (kind.lowercase()) {
           "big_foot" -> ResponseEntity.internalServerError().build()
            "snail" -> ResponseEntity.status(504).build()
           else -> ResponseEntity.ok(PetNameGenerator.generate(kind))
        }
    }

    @GetMapping("/api/v1/pet-price")
    suspend fun getPetPriceV1(@RequestParam kind: String) : ResponseEntity<Any> {
        delay(Random.nextLong(100, 500))
        return ResponseEntity.ok( PetPrice(kind.length*200.0, "EUR"))
    }

    @GetMapping("/api/v2/pet-price")
    suspend fun getPetPriceV2(@RequestParam kind: String) : ResponseEntity<Any> {
        delay(Random.nextLong(100, 500))
        return if (Random.nextBoolean()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok( PetPrice(kind.length*100.0, "EUR"))
        }
    }
}

data class PetName(val name: String)
data class PetPrice(val price: Double, val currency: String)
object PetNameGenerator {
    private val dogNames = listOf("Fido", "Rex", "Buddy", "Max", "Charlie")
    private val catNames = listOf("Whiskers", "Mittens", "Shadow", "Simba", "Luna")
    private val unicornNames = listOf("Twinkle Sparkle", "Rarity",  "DJ Pon-3", "Film", "Flam")
    fun generate(kind: String): PetName {
        val names = when (kind.lowercase()) {
            "dog" -> dogNames
            "cat" -> catNames
            "unicorn" -> unicornNames
            else -> listOf("Doe")
        }
        val name = names.random()
        return PetName(name)
    }
}