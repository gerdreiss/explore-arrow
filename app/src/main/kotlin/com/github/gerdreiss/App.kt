package com.github.gerdreiss

import arrow.core.NonEmptySet
import arrow.core.None
import arrow.core.Some
import arrow.core.raise.either
import com.github.gerdreiss.explore.either.buildUser
import com.github.gerdreiss.explore.ior.exploreIor
import com.github.gerdreiss.explore.optics.Address
import com.github.gerdreiss.explore.optics.Age
import com.github.gerdreiss.explore.optics.City
import com.github.gerdreiss.explore.optics.Country
import com.github.gerdreiss.explore.optics.Name
import com.github.gerdreiss.explore.optics.Person
import com.github.gerdreiss.explore.optics.Street
import com.github.gerdreiss.explore.optics.capitalizeCountryCopy
import com.github.gerdreiss.explore.optics.capitalizeCountryModify
import com.github.gerdreiss.explore.optics.capitalizeCountryWhere

fun main() {
    exploreOptics()
    exploreIorAndPatternMatching()
    exploreEither()
}

private fun exploreOptics() {
    val p =
        Person(
            Name("John Doe"),
            Age(40),
            NonEmptySet.of(
                setOf(
                    Address(Street(Name("Main"), 40), City(Name("Dublin"), Country("ireland"))),
                    Address(Street(Name("Bob Williams"), 4), City(Name("Cork"), Country("ireland"))),
                ),
            ),
        )

    println(p.capitalizeCountryModify())
    println(p.capitalizeCountryCopy())
    println(p.capitalizeCountryWhere { it.city.name.value != "Dublin" })
}

private fun exploreIorAndPatternMatching() {
    when (val maybeIor = exploreIor()) {
        None -> {
            println("Ior is empty")
        }

        is Some -> {
            maybeIor.value
                .fold(
                    { left -> println("Ior is left: $left") },
                    { right -> println("Ior is right: $right") },
                    { left, right -> println("Ior is both: left = $left, right = $right") },
                )
        }
    }
}

fun exploreEither() {
    either {
        val user1 = buildUser("Ivan", 40, "Murmansk").bind()
        val user2 = buildUser("Masha", 30, "Murmansk").bind()
        user1 to user2
    }.fold(
        { left -> println("Building User objects failed: $left") },
        { right -> println("Users built: $right") },
    )
}
