package de.neusta.validationdsldemo

import de.neusta.validationdsldemo.MultiValidationService.Companion.validate
import de.neusta.validationdsldemo.MultiValidationService.Successful

class KirschCreation {

    private val kirschRepository: KirschRepository = KirschRepository()

    fun createKirsche(
        name: String,
        color: String,
        size: Int,
    ): CreationResult {
        val domainObjectCreationResult = Kirsche(
            name = name,
            farbe = color,
            groesse = size,
        )

        val validationResult = validate {
            require("Kirsche existiert schon") {
                !kirschRepository.existsByNameAndColor(name = name, color = color)
            }
            requireNoErrors(domainObjectCreationResult)
        }

        return when (validationResult) {
            is MultiValidationService.Error -> Error(messages = validationResult.errors)
            is Successful -> {
                val kirsche = (domainObjectCreationResult as Kirsche.Created).kirsche
                kirschRepository.save(kirsche)
                Created(kirsche = kirsche)
            }
        }
    }

    sealed class CreationResult
    class Created(val kirsche: Kirsche) : CreationResult()
    class Error(val messages: List<String>) : CreationResult()
}