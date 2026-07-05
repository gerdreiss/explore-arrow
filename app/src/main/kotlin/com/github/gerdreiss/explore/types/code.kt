package com.github.gerdreiss.explore.types

import arrow.core.Ior
import arrow.core.Option
import arrow.core.toOption
import kotlin.random.Random

fun exploreIor(): Option<Ior<String, Int>> = Ior.fromNullables(name(), age()).toOption()

fun name(): String? = if (Random.nextBoolean()) "explore" else null

fun age(): Int? = if (Random.nextBoolean()) Random.nextInt(0, 100) else null
