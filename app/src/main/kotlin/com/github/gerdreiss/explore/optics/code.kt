package com.github.gerdreiss.explore.optics

import arrow.core.NonEmptySet
import arrow.optics.DelicateOptic
import arrow.optics.copy
import arrow.optics.dsl.every
import arrow.optics.dsl.filter
import arrow.optics.optics

@optics
data class Person(val name: String, val age: Int, val addresses: NonEmptySet<Address>) {
    companion object
}

@optics
data class Address(val street: Street, val city: City) {
    companion object
}

@optics
data class Street(val name: String, val number: Int?) {
    companion object
}

@optics
data class City(val name: String, val country: String) {
    companion object
}

fun String.capitalize(): String = this.replaceFirstChar { it.uppercaseChar() }

fun Person.capitalizeCountryModify(): Person =
    Person.addresses.every.city.country.modify(this) { it.capitalize() }

fun Person.capitalizeCountryCopy(): Person =
    this.copy { Person.addresses.every.city.country transform { it.capitalize() } }

@OptIn(DelicateOptic::class)
fun Person.capitalizeCountryWhere(p: (Address) -> Boolean): Person =
    this.copy { Person.addresses.every.filter(p).city.country transform { it.capitalize() } }
