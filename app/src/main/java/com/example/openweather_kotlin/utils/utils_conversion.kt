package com.example.openweather_kotlin.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object utils_conversion {
    fun stringDateFormatter(datetime: String, formatPatternBefore: String, formatPatternTarget: String) : String {
        var dateBeforeFormat = DateTimeFormatter.ofPattern(formatPatternBefore)
        var dateTargetFormatter = DateTimeFormatter.ofPattern(formatPatternTarget)
        return LocalDateTime.parse(datetime,dateBeforeFormat).format(dateTargetFormatter)
    }
}
