package de.neusta.validationdsldemo

fun main(args: Array<String>) {

    val falscherApfel = Apfel(
        name = "",
        farbe = " ",
        groesse = 0,
    )

    val falscheBirne = Birne(
        name = "",
        farbe = " ",
        groesse = 0,
    )

    val falscheKirsche = KirschCreation().createKirsche(
        name = "Superkirsche",
        color = "rot",
        size = 0,
    )

    println()
}