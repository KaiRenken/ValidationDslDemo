package de.neusta.validationdsldemo

import de.neusta.validationdsldemo.ValidationService.Companion.validate
import de.neusta.validationdsldemo.ValidationService.ErrorsOccurred
import de.neusta.validationdsldemo.ValidationService.Successful

class KirschCreation() {

    private val kirschRepository: KirschRepository = KirschRepository()

    fun createKirsche(
        name: String,
        color: String,
        size: Int,
    ): CreationResult {
        val domainObjectCreationResult = Kirsche(
            name = name,
            color = color,
            size = size,
        )

        val validationResult = validate(Kirsche::class) {
            require("Kirsche already exists") {
                !kirschRepository.existsByNameAndColor(name = name, color = color)
            }
            requireNoErrors(domainObjectCreationResult)
        }

        return when (validationResult) {
            is ErrorsOccurred -> Failure(errors = validationResult.errors)
            is Successful -> {
                val kirsche = (domainObjectCreationResult as Kirsche.Created).kirsche
                kirschRepository.save(kirsche)
                Created(kirsche = kirsche)
            }
        }
    }

    sealed class CreationResult
    class Created(val kirsche: Kirsche) : CreationResult()
    class Failure(val errors: List<String>) : CreationResult()
}