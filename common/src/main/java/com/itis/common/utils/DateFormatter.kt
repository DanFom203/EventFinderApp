package com.itis.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatter {

    private val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private val simpleDateTimeFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    private val simpleTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun formatDate(date: Date): String {
        return simpleDateFormat.format(date)
    }

    fun formatDateTime(date: Date): String {
        return simpleDateTimeFormat.format(date)
    }

    fun formatTime(date: Date): String {
        return simpleTimeFormat.format(date)
    }

    fun formatEpochSecondsToDate(seconds: Long): Date {
        return Date(seconds * 1000)  // Преобразование секунд в миллисекунды
    }

    fun verifyStartDate(startDate: Long): String {
        return if (startDate <= Constants.YEAR_2022_TIME_DATA ||
            startDate >= Constants.FUTURE_TIME_DATA
        ) {
            "Open-started event"
        } else {
            formatDateTime(
                formatEpochSecondsToDate(startDate)
            )
        }
    }

    fun verifyEndDate(endDate: Long): String {
        return if (endDate <= Constants.YEAR_2024_TIME_DATA ||
            endDate >= Constants.FUTURE_TIME_DATA
        ) {
            "Open-ended event"
        } else {
            formatDateTime(
                formatEpochSecondsToDate(endDate)
            )
        }
    }
}