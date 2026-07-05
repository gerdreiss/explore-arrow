package com.github.gerdreiss

import arrow.core.NonEmptySet
import com.github.gerdreiss.explore.optics.*

fun main() {
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
