package de.neusta.validationdsldemo

import kotlin.reflect.KClass

class ValidationService<T> private constructor(
    private val errors: MutableList<String> = ArrayList(),
    private var result: T? = null
) {
    fun require(
        msg: String,
        checker: () -> Boolean,
    ) {
        if (!checker.invoke()) errors.add(msg)
    }

    fun requireNoErrors(
        validatable: Validatable<T>
    ) {
        when (validatable) {
            is Errors<T> -> validatable.errors.forEach { errors.add(it) }
            is Success<T> -> result = validatable.result
        }
    }

    companion object {
        fun <T : Any> validate(clazz: KClass<T>, block: ValidationService<T>.() -> Unit): ValidationResult<T> {
            val validationService = ValidationService<T>()
            validationService.apply(block)
            if (validationService.errors.isEmpty()) return Successful(result = validationService.result)
            return ErrorsOccurred(errors = validationService.errors.toList())
        }
    }

    interface Validatable<T>

    interface Errors<T> : Validatable<T> {
        val errors: List<String>
    }

    interface Success<T> : Validatable<T> {
        val result: T?
    }

    sealed class ValidationResult<T>
    class Successful<T>(val result: T?) : ValidationResult<T>(), Validatable<T>
    class ErrorsOccurred<T>(val errors: List<String>) : ValidationResult<T>(), Validatable<T>
}