package de.neusta.validationdsldemo

class KirschRepository {

    fun existsByNameAndColor(name: String, color: String): Boolean = true

    fun save(kirsche: Kirsche): Unit {
        println("Kirsche saved!")
    }
}
