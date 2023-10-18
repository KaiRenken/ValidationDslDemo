package de.neusta.validationdsldemo

fun main(args: Array<String>) {

    val falscherApfel = Apfel(
        name = "",
        color = " ",
        size = 0,
    )

    val falscheBirne = Birne(
        name = "",
        color = " ",
        size = 0,
    )

    val falscheKirsche = KirschCreation().createKirsche(
        name = "Geile Kirsche",
        color = "Rot",
        size = 0,
    )

    println()
}