package com.github.gerdreiss.explore.optics

import arrow.core.NonEmptySet
import arrow.optics.DelicateOptic
import arrow.optics.copy
import arrow.optics.dsl.every
import arrow.optics.dsl.filter
import arrow.optics.optics

@JvmInline
value class Name(
    val value: String,
)

@JvmInline
value class Age(
    val value: Int,
)

@JvmInline
value class Country(
    val name: String,
)

@optics
data class Address(
    val street: Street,
    val city: City,
) {
    companion object
}

@optics
data class Street(
    val name: Name,
    val number: Int?,
) {
    companion object
}

@optics
data class City(
    val name: Name,
    val country: Country,
) {
    companion object
}

@optics
data class Person(
    val name: Name,
    val age: Age,
    val addresses: NonEmptySet<Address>,
) {
    companion object
}

fun String.capitalize(): String = this.replaceFirstChar { it.uppercaseChar() }

fun Person.capitalizeCountryModify(): Person =
    Person.addresses.every.city.country
        .modify(this) { Country(it.name.capitalize()) }

fun Person.capitalizeCountryCopy(): Person =
    this.copy {
        Person.addresses.every.city.country transform { Country(it.name.capitalize()) }
    }

@OptIn(DelicateOptic::class)
fun Person.capitalizeCountryWhere(predicate: (Address) -> Boolean): Person =
    Person.addresses.every
        .filter(predicate)
        .city.country
        .modify(this) { Country(it.name.capitalize()) }
