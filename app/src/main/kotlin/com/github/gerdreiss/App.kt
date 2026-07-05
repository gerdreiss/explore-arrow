package com.github.gerdreiss

import arrow.core.NonEmptySet
import com.github.gerdreiss.explore.optics.*


fun main() {
    val p = Person(
        name = "John Doe",
        age = 40,
        addresses = NonEmptySet.of(
            setOf(
                Address(
                    street = Street(name = "Main", number = 40),
                    city = City(name = "Dublin", country = "ireland")
                ),
                Address(
                    street = Street(name = "Bob Williams", number = 4),
                    city = City(name = "Cork", country = "ireland")
                )
            )
        ),
    )

    println(p.capitalizeCountryModify())
    println(p.capitalizeCountryCopy())
    println(p.capitalizeCountryWhere { it.city.name != "Dublin" })
}
