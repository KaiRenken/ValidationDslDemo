package de.neusta.validationdsldemo

class Apfel private constructor(
    val name: String,
    val farbe: String,
    val groesse: Int,
) {

    init {
        require(name.isNotBlank()) { "Name darf nicht leer sein" }
        require(farbe.isNotBlank()) { "Farbe darf nicht leer sein" }
        require(groesse > 0) { "Groesse muss größer als 0 sein" }
    }

    companion object {
        operator fun invoke(
            name: String,
            farbe: String,
            groesse: Int,
        ): CreationResult = try {
            Created(
                apfel = Apfel(
                    name = name,
                    farbe = farbe,
                    groesse = groesse,
                )
            )
        } catch (exception: IllegalArgumentException) {
            Failure(error = exception.message!!)
        }
    }

    sealed class CreationResult
    class Created(val apfel: Apfel) : CreationResult()
    class Failure(val error: String) : CreationResult()
}