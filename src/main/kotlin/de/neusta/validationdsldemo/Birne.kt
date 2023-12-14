package de.neusta.validationdsldemo

import de.neusta.validationdsldemo.ValidationService.Companion.validate

class Birne private constructor(
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

                is ValidationService.Error -> Error(messages = validationResult.errors)

                is ValidationService.Success -> Created(
                    birne = Birne(
                        name = name,
                        farbe = farbe,
                        groesse = groesse,
                    )
                )

            }
        }
    }

    sealed class CreationResult
    class Created(val birne: Birne) : CreationResult()
    class Error(val messages: List<String>) : CreationResult()
}