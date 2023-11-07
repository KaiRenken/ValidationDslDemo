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
        name = "Geile Kirsche",
        color = "Rot",
        size = 0,
    )

    println()
}