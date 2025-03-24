package com.example.cocktail_app.data

data class Cocktail(val name: String, val ingredients: List<String>, val preparation: String)


val cocktails = listOf(
    Cocktail("Mojito", listOf("Rum", "Mięta", "Limonka", "Cukier", "Woda gazowana"), "Wymieszaj składniki i podawaj z lodem."),
    Cocktail("Margarita", listOf("Tequila", "Limonka", "Triple sec"), "Wstrząśnij składniki i podawaj w szklance z solą."),
    Cocktail("Piña Colada", listOf("Rum", "Ananas", "Mleczko kokosowe"), "Zmiksuj składniki z lodem i podawaj."),
    Cocktail("Caipirinha", listOf("Cachaça", "Limonka", "Cukier trzcinowy"), "Rozgnieć limonkę z cukrem, dodaj cachaçę i lód."),
    Cocktail("Daiquiri", listOf("Rum", "Limonka", "Cukier"), "Wstrząśnij składniki i podawaj schłodzone."),
    Cocktail("Old Fashioned", listOf("Whiskey", "Cukier", "Angostura", "Woda"), "Rozpuść cukier z bittersami, dodaj whiskey i lód."),
    Cocktail("Cosmopolitan", listOf("Wódka", "Triple sec", "Żurawina", "Limonka"), "Wstrząśnij składniki i podawaj schłodzone."),
    Cocktail("Mai Tai", listOf("Rum", "Triple sec", "Limonka", "Orgeat"), "Wstrząśnij składniki i podawaj z lodem."),
    Cocktail("Negroni", listOf("Gin", "Campari", "Słodki wermut"), "Wymieszaj składniki i podawaj z lodem oraz skórką pomarańczy."),
    Cocktail("Whiskey Sour", listOf("Whiskey", "Cytryna", "Cukier", "Białko jajka"), "Wstrząśnij składniki i podawaj schłodzone."),
    Cocktail("Moscow Mule", listOf("Wódka", "Limonka", "Piwo imbirowe"), "Podawaj w miedzianym kubku z lodem."),
    Cocktail("Tequila Sunrise", listOf("Tequila", "Pomarańcza", "Grenadyna"), "Wlej składniki warstwowo i podawaj."),
    Cocktail("Long Island Iced Tea", listOf("Wódka", "Tequila", "Rum", "Gin", "Triple sec", "Cytryna", "Cola"), "Wymieszaj składniki i podawaj z lodem."),
    Cocktail("Bloody Mary", listOf("Wódka", "Sok pomidorowy", "Cytryna", "Sól", "Pieprz", "Tabasco", "Sos Worcestershire"), "Wymieszaj składniki i podawaj z lodem."),
)