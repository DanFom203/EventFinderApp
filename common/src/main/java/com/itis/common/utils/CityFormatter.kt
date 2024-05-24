package com.itis.common.utils

class CityFormatter {

    private val cityToAbbreviationMap = mapOf(
        "Казань" to "kzn",
        "Москва" to "msk",
        "Санкт-Петербург" to "spb",
        "Новосибирск" to "nsk",
        "Екатеринбург" to "ekb",
        "Нижний Новгород" to "nnv",
        "Выборг" to "vbg",
        "Самара" to "smr",
        "Краснодар" to "krd",
        "Сочи" to "sochi",
        "Уфа" to "ufa",
        "Красноярск" to "krasnoyarsk"
    )

    private val abbreviationToCityMap = cityToAbbreviationMap.entries.associate { (key, value) -> value to key }

    fun cityToAbbreviation(city: String): String? {
        return cityToAbbreviationMap[city]
    }

    fun abbreviationToCity(abbreviation: String): String? {
        return if (abbreviation != "Unknown location") {
            abbreviationToCityMap[abbreviation]
        } else {
            "Unknown location"
        }
    }
}