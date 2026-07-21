package com.github.gerdreiss.explore.ior

import arrow.core.Ior
import arrow.core.Option
import arrow.core.raise.IorRaise
import arrow.core.raise.context.ior
import arrow.core.toOption
import kotlin.random.Random

fun exploreIor(): Option<Ior<String, Int>> = Ior.fromNullables(name(), age()).toOption()

fun name(): String? = if (Random.nextBoolean()) "explore" else null

fun age(): Int? = if (Random.nextBoolean()) Random.nextInt(0, 100) else null

context(notices: IorRaise<List<String>>)
fun normalizeTitle(raw: String): String {
    val title = raw.trim()
    if (title != raw) notices.accumulate(listOf("leading or trailing whitespace removed"))
    return title
}

fun normalizedTitle(raw: String): Ior<List<String>, String> =
    ior(List<String>::plus) { normalizeTitle(raw) }
