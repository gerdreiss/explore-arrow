package com.github.gerdreiss

import arrow.core.NonEmptySet
import arrow.core.None
import arrow.core.Some
import arrow.core.raise.either
import com.github.gerdreiss.explore.either.UserId
import com.github.gerdreiss.explore.either.buildUser
import com.github.gerdreiss.explore.either.fromTheSameCity
import com.github.gerdreiss.explore.ior.exploreIor
import com.github.gerdreiss.explore.ior.normalizedTitle
import com.github.gerdreiss.explore.optics.*

fun main() {
    exploreOptics()
    exploreIorAndPatternMatching()
    exploreEither1()
    exploreEither2()
    exploreIor2()
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

private fun exploreIorAndPatternMatching() = //
    when (val maybeIor = exploreIor()) {
        None -> {
            println("Ior is empty")
        }

        is Some -> {
            maybeIor.value
                .fold(
                    { println("Ior is left: $it") },
                    { println("Ior is right: $it") },
                    { left, right -> println("Ior is both: left = $left, right = $right") },
                )
        }
    }

fun exploreEither1() = //
    either {
        val user1 = buildUser("Ivan", 40, "Murmansk").bind()
        val user2 = buildUser("Masha", 30, "Murmansk").bind()
        user1 to user2
    }.fold(
        { println("Building User objects failed: $it") },
        { (user1, user2) -> println("Users built: $user1, $user2") },
    )

fun exploreEither2() = //
    fromTheSameCity(UserId("1"), UserId("2"))
        .fold(
            { println("Finding User objects failed: $it") },
            {
                if (it) {
                    println("Users are from the same city")
                } else {
                    println("Users are not from the same city")
                }
            },
        )

fun exploreIor2() = //
    normalizedTitle(" bla bloop ")
        .fold(
            { println("Ior.Left: $it") },
            { println("Ior.Right: $it") },
            { left, right -> println("Ior.Both: $left, $right") },
        )