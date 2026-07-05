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
        name = Name("John Doe"),
        age = Age(40),
        addresses = NonEmptySet.of(
            setOf(
                Address(
                    street = Street(name = Name("Main"), number = 40),
                    city = City(name = Name("Dublin"), country = Country("ireland"))
                ),
                Address(
                    street = Street(name = Name("Bob Williams"), number = 4),
                    city = City(name = Name("Cork"), country = Country("ireland"))
                )
            )
        ),
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
                { left -> println("Ior is left: ${left}") },
                { right -> println("Ior is right: ${right}") },
                { left, right -> println("Ior is both: left = ${left}, right = ${right}") }
            )
    }
}
