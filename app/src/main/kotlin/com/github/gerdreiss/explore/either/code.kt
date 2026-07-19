package com.github.gerdreiss.explore.either

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.left
import arrow.core.raise.ExperimentalRaiseAccumulateApi
import arrow.core.raise.accumulate
import arrow.core.raise.either
import arrow.core.right
import kotlin.random.Random
import kotlin.random.nextUInt

sealed class UserProblem {
    object EmptyName : UserProblem()

    data class NegativeAge(val age: Int) : UserProblem()
}

sealed interface UserError {
    val message: String
}

data class UserNotFound(override val message: String) : UserError

@JvmInline
value class UserId(val value: String)

@JvmInline
value class Name(val value: String)

@JvmInline
value class Age(val value: Int)

@JvmInline
value class City(val value: String)

data class User(
    val id: UserId,
    val name: Name,
    val age: Age,
    val city: City,
)

@OptIn(ExperimentalRaiseAccumulateApi::class)
fun buildUser(
    name: String,
    age: Int,
    city: String,
): Either<NonEmptyList<UserProblem>, User> = //
    either {
        accumulate {
            ensureOrAccumulate(name.isNotEmpty()) { UserProblem.EmptyName }
            ensureOrAccumulate(age >= 0) { UserProblem.NegativeAge(age) }
            User(UserId(Random.nextUInt().toString()), Name(name), Age(age), City(city))
        }
    }

// these signatures mark the possible errors
fun findUser(id: UserId): Either<UserNotFound, User> = //
    if (Random.nextBoolean()) {
        User(id, Name("Ivan"), Age(40), City("Murmansk")).right()
    } else {
        UserNotFound("User not found").left()
    }

// you build larger computations using the 'Raise' DSL
fun fromTheSameCity(
    id1: UserId,
    id2: UserId,
): Either<UserNotFound, Boolean> = //
    either {
        // this begins a 'Raise' block
        val user1 = findUser(id1).bind() // 'bind' aborts computation on failure
        val user2 = findUser(id2).bind()
        return (user1.city == user2.city).right()
    }
