package com.itis.feature.events.impl.utils

class DescriptionFormatter {

    // Метод для удаления HTML тегов из строки
    fun format(description: String): String {
        return description.replace(Regex("<[^>]*>"), "")
    }
}