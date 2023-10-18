package de.neusta.validationdsldemo

import de.neusta.validationdsldemo.ValidationService.*
import de.neusta.validationdsldemo.ValidationService.Companion.validate

class Kirsche private constructor(
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
            val validationResult = validate(Kirsche::class) {
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
                    kirsche = Kirsche(
                        name = name,
                        color = color,
                        size = size,
                    )
                )
            }
        }
    }

    sealed class CreationResult : Validatable<Kirsche>
    class Created(val kirsche: Kirsche) : CreationResult()
    class Failure(override val errors: List<String>) : CreationResult(), Errors<Kirsche>
}