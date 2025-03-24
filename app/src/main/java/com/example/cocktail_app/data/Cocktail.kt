package com.example.cocktail_app.data

data class Cocktail(val name: String, val ingredients: List<String>, val preparation: String)


val cocktails = listOf(
    Cocktail("Mojito", listOf("Rum", "Mięta", "Limonka", "Cukier", "Woda gazowana"), "Wymieszaj składniki i podawaj z lodem."),
    Cocktail("Margarita", listOf("Tequila", "Limonka", "Triple sec"), "Wstrząśnij składniki i podawaj w szklance z solą."),
    Cocktail("Piña Colada", listOf("Rum", "Ananas", "Mleczko kokosowe"), "Zmiksuj składniki z lodem i podawaj.")
)