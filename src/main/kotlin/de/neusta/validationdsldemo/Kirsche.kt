package de.neusta.validationdsldemo

import de.neusta.validationdsldemo.ValidationService.*
import de.neusta.validationdsldemo.ValidationService.Companion.validate

class Kirsche private constructor(
    val name: String,
    val farbe: String,
    val groesse: Int,
) {

    companion object {
        operator fun invoke(
            name: String,
            farbe: String,
            groesse: Int,
        ): CreationResult {
            val validationResult = validate(Kirsche::class) {
                require("Name darf nicht leer sein") {
                    name.isNotBlank()
                }
                require("Farbe darf nicht leer sein") {
                    farbe.isNotBlank()
                }
                require("Groesse muss größer als 0 sein") {
                    groesse > 0
                }
            }

            return when (validationResult) {
                is ErrorsOccurred -> Failure(errors = validationResult.errors)
                is Successful -> Created(
                    kirsche = Kirsche(
                        name = name,
                        farbe = farbe,
                        groesse = groesse,
                    )
                )
            }
        }
    }

    sealed class CreationResult : Validatable<Kirsche>
    class Created(val kirsche: Kirsche) : CreationResult()
    class Failure(override val errors: List<String>) : CreationResult(), Errors<Kirsche>
}