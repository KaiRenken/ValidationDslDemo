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