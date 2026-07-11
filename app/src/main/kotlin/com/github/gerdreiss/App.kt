package com.github.gerdreiss

import arrow.core.NonEmptySet
import arrow.core.None
import arrow.core.Some
import com.github.gerdreiss.explore.optics.*
import com.github.gerdreiss.explore.types.exploreIor

fun main() {
    exploreOptics()
    exploreIorAndPatternMatching()
}

private fun exploreOptics() {
    val p = Person(
        Name("John Doe"),
        Age(40),
        NonEmptySet.of(
            setOf(
                Address(Street(Name("Main"), 40), City(Name("Dublin"), Country("ireland"))),
                Address(Street(Name("Bob Williams"), 4), City(Name("Cork"), Country("ireland")))
            )
        )
    )

    println(p.capitalizeCountryModify())
    println(p.capitalizeCountryCopy())
    println(p.capitalizeCountryWhere { it.city.name.value != "Dublin" })
}

private fun exploreIorAndPatternMatching() {
    when (val maybeIor = exploreIor()) {
        None -> println("Ior is empty")
        is Some -> maybeIor.value
            .fold(
                { left -> println("Ior is left: $left") },
                { right -> println("Ior is right: $right") },
                { left, right -> println("Ior is both: left = ${left}, right = $right") }
            )
    }
}
