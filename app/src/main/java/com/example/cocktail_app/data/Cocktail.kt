package com.example.cocktail_app.data

import com.example.cocktail_app.R

data class Cocktail(val name: String, val imageResId: Int, val ingredients: List<String>, val preparation: String, val isAlcoholic: Boolean)


val cocktails = listOf(
    Cocktail("Mojito", R.drawable.mojito, listOf("Rum", "Mięta", "Limonka", "Cukier", "Woda gazowana"), "Wymieszaj składniki i podawaj z lodem.", true),
    Cocktail("Margarita", R.drawable.margarita, listOf("Tequila", "Limonka", "Triple sec"), "Wstrząśnij składniki i podawaj w szklance z solą.", true),
    Cocktail("Piña Colada", R.drawable.pina_colada, listOf("Rum", "Ananas", "Mleczko kokosowe"), "Zmiksuj składniki z lodem i podawaj.", true),
    Cocktail("Caipirinha", R.drawable.caipirinha, listOf("Cachaça", "Limonka", "Cukier trzcinowy"), "Rozgnieć limonkę z cukrem, dodaj cachaçę i lód.", true),
    Cocktail("Daiquiri", R.drawable.daiquiri, listOf("Rum", "Limonka", "Cukier"), "Wstrząśnij składniki i podawaj schłodzone.", true),
    Cocktail("Old Fashioned", R.drawable.old_fashioned, listOf("Whiskey", "Cukier", "Angostura", "Woda"), "Rozpuść cukier z bittersami, dodaj whiskey i lód.", true),
    Cocktail("Cosmopolitan", R.drawable.cosmopolitan, listOf("Wódka", "Triple sec", "Żurawina", "Limonka"), "Wstrząśnij składniki i podawaj schłodzone.", true),
    Cocktail("Mai Tai", R.drawable.mai_tai, listOf("Rum", "Triple sec", "Limonka", "Orgeat"), "Wstrząśnij składniki i podawaj z lodem.", true),
    Cocktail("Negroni", R.drawable.negroni, listOf("Gin", "Campari", "Słodki wermut"), "Wymieszaj składniki i podawaj z lodem oraz skórką pomarańczy.", true),
    Cocktail("Whiskey Sour", R.drawable.whiskey_sour, listOf("Whiskey", "Cytryna", "Cukier", "Białko jajka"), "Wstrząśnij składniki i podawaj schłodzone.", true),
    Cocktail("Moscow Mule", R.drawable.moscow_mule, listOf("Wódka", "Limonka", "Piwo imbirowe"), "Podawaj w miedzianym kubku z lodem.", true),
    Cocktail("Tequila Sunrise", R.drawable.tequila_sunrise, listOf("Tequila", "Pomarańcza", "Grenadyna"), "Wlej składniki warstwowo i podawaj.", true),
    Cocktail("Long Island", R.drawable.long_island_iced_tea, listOf("Wódka", "Tequila", "Rum", "Gin", "Triple sec", "Cytryna", "Cola"), "Wymieszaj składniki i podawaj z lodem.", true),
    Cocktail("Bloody Mary", R.drawable.bloody_mary, listOf("Wódka", "Sok pomidorowy", "Cytryna", "Sól", "Pieprz", "Tabasco", "Sos Worcestershire"), "Wymieszaj składniki i podawaj z lodem.", true),
    Cocktail("Virgin Mojito", R.drawable.virgin_mojito, listOf("Mięta", "Limonka", "Cukier", "Woda gazowana"), "Wymieszaj składniki i podawaj z lodem.", false),
    Cocktail("Shirley Temple", R.drawable.shirley_temple, listOf("Lemoniada", "Grenadyna", "Wiśnia koktajlowa"), "Wlej składniki do szklanki z lodem i delikatnie wymieszaj.", false),
    Cocktail("Nojito", R.drawable.nojito, listOf("Mięta", "Limonka", "Cukier", "Sprite"), "Wymieszaj składniki i podawaj z lodem.", false),
    Cocktail("Tropikalny Koktajl", R.drawable.tropikalny_koktajl, listOf("Sok pomarańczowy", "Sok ananasowy", "Grenadyna"), "Wlej składniki warstwowo i podawaj schłodzone.", false),
    Cocktail("Smoothie Truskawkowe", R.drawable.smoothie_truskawkowe, listOf("Truskawki", "Jogurt", "Miód", "Mleko"), "Zmiksuj wszystkie składniki na gładką masę.", false),
    Cocktail("Lemoniada Ogórkowa", R.drawable.lemoniada_ogorkowa, listOf("Cytryna", "Ogórek", "Woda", "Miód"), "Zmiksuj składniki i podawaj z lodem.", false),
    Cocktail("Iced Tea", R.drawable.iced_tea_brzoskwiniowa, listOf("Herbata", "Syrop brzoskwiniowy", "Lód", "Cytryna"), "Wymieszaj i podawaj schłodzone.", false),
    Cocktail("Mango Lassi", R.drawable.mango_lassi, listOf("Mango", "Jogurt", "Mleko", "Cukier", "Kardamon"), "Zmiksuj wszystko do uzyskania gładkiej konsystencji.", false)
)