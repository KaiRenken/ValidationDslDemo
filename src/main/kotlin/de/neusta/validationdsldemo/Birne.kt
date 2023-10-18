package de.neusta.validationdsldemo

import de.neusta.validationdsldemo.ValidationService.Companion.validate
import de.neusta.validationdsldemo.ValidationService.ErrorsOccurred
import de.neusta.validationdsldemo.ValidationService.Successful

class Birne private constructor(
    val name: String,
    val color: String,
    val size: Int,
) {

    companion object {
        operator fun invoke(
            name: String,
            color: String,
            size: Int,
        ): CreationResult {
            val validationResult = validate(Birne::class) {
                require("Name must not be blank") {
                    name.isNotBlank()
                }
                require("Color must not be blank") {
                    color.isNotBlank()
                }
                require("Size must be a positive number") {
                    size > 0
                }
            }

            return when (validationResult) {
                is ErrorsOccurred -> Failure(errors = validationResult.errors)
                is Successful -> Created(
                    birne = Birne(
                        name = name,
                        color = color,
                        size = size,
                    )
                )
            }
        }
    }

    sealed class CreationResult
    class Created(val birne: Birne) : CreationResult()
    class Failure(val errors: List<String>) : CreationResult()
}