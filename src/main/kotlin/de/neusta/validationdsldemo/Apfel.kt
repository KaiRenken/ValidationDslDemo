package de.neusta.validationdsldemo

class Apfel private constructor(
    val name: String,
    val color: String,
    val size: Int,
) {

    init {
        require(name.isNotBlank()) { "Name must not be blank" }
        require(color.isNotBlank()) { "Color must not be blank" }
        require(size > 0) { "Size must be a positive number" }
    }

    companion object {
        operator fun invoke(
            name: String,
            color: String,
            size: Int,
        ): CreationResult = try {
            Created(
                apfel = Apfel(
                    name = name,
                    color = color,
                    size = size,
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