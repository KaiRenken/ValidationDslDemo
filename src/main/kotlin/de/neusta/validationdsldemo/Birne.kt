package de.neusta.validationdsldemo

import de.neusta.validationdsldemo.ValidationService.Companion.validate
import de.neusta.validationdsldemo.ValidationService.ErrorsOccurred
import de.neusta.validationdsldemo.ValidationService.Successful

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
            val validationResult = validate(Birne::class) {
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
    class Failure(val errors: List<String>) : CreationResult()
}