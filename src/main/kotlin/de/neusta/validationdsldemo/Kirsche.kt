package de.neusta.validationdsldemo

import de.neusta.validationdsldemo.MultiValidationService.*
import de.neusta.validationdsldemo.MultiValidationService.Companion.validate

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
            val validationResult = validate {
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

                is MultiValidationService.Error -> Error(messages = validationResult.errors)

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

    sealed class CreationResult : Validatable
    class Created(val kirsche: Kirsche) : CreationResult()
    class Error(override val messages: List<String>) : CreationResult(), Errors
}