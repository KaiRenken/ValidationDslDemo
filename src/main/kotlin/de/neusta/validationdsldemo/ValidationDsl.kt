package de.neusta.validationdsldemo

class ValidationService private constructor(
    private val errors: MutableList<String> = ArrayList()
) {
    fun require(
        message: String,
        checker: () -> Boolean,
    ) {
        if (!checker.invoke()) errors.add(message)
    }

    companion object {
        fun validate(block: ValidationService.() -> Unit): ValidationResult {
            val validationService = ValidationService()
            validationService.apply(block)
            if (validationService.errors.isEmpty()) return Success
            return Error(errors = validationService.errors.toList())
        }
    }

    sealed class ValidationResult
    data object Success : ValidationResult()
    class Error(val errors: List<String>) : ValidationResult()
}

class MultiValidationService private constructor(
    private val errors: MutableList<String> = ArrayList(),
    private var result: Any? = null
) {
    fun require(
        message: String,
        checker: () -> Boolean,
    ) {
        if (!checker.invoke()) errors.add(message)
    }

    fun requireNoErrors(
        validatable: Validatable
    ) {
        when (validatable) {
            is Errors -> validatable.messages.forEach { errors.add(it) }
            is Success -> result = validatable.result
        }
    }

    companion object {
        fun validate(block: MultiValidationService.() -> Unit): ValidationResult {
            val multiValidationService = MultiValidationService()
            multiValidationService.apply(block)
            if (multiValidationService.errors.isEmpty()) return Successful(result = multiValidationService.result)
            return ErrorsOccurred(errors = multiValidationService.errors.toList())
        }
    }

    interface Validatable

    interface Errors : Validatable {
        val messages: List<String>
    }

    interface Success : Validatable {
        val result: Any?
    }

    sealed class ValidationResult
    class Successful(val result: Any?) : ValidationResult(), Validatable
    class ErrorsOccurred(val errors: List<String>) : ValidationResult(), Validatable
}