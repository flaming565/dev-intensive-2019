package ru.skillbranch.devintensive.extensions;

import java.text.SimpleDateFormat
import java.util.*
import kotlin.String

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    this.time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(number: Long): String {
        val isLessFive: Boolean = number % 10 in 1..4
        val unitStr = when (this) {
            SECOND -> if (isLessFive) "секунды" else "секунд"
            MINUTE -> if (isLessFive) "минуты" else "минут"
            HOUR -> if (isLessFive) "часа" else "часов"
            DAY -> if (isLessFive) "дня" else "дней"
        }
        return "$number $unitStr"
    }
}

fun Date.humanizeDiff(date: Date = Date()): String {
    return when (val value = date.time - this.time) {
        in Long.MIN_VALUE..-360 * DAY -> "более чем через год"
        in -360 * DAY..-26 * HOUR -> "через ${TimeUnits.DAY.plural(-value / DAY)}"
        in -26 * HOUR..-22 * HOUR -> "через день"
        in -22 * HOUR..-75 * MINUTE -> "через ${TimeUnits.HOUR.plural(-value / HOUR)}"
        in -75 * MINUTE..-45 * MINUTE -> "через час"
        in -45 * MINUTE..-75 -> "через ${TimeUnits.MINUTE.plural(-value / MINUTE)}"
        in -75..-45 -> "через минуту"
        in -45..-1 -> "через несколько секунд"
        in -1..1 -> "только что"
        in 1..45 -> "несколько секунд назад"
        in 45..75 -> "минуту назад"
        in 75..45 * MINUTE -> "${TimeUnits.MINUTE.plural(value / MINUTE)} назад"
        in 45 * MINUTE..75 * MINUTE -> "час назад"
        in 75 * MINUTE..22 * HOUR -> "${TimeUnits.HOUR.plural(value / HOUR)} назад"
        in 22 * HOUR..26 * HOUR -> "день назад"
        in 26 * HOUR..360 * DAY -> "${TimeUnits.DAY.plural(value / DAY)} назад"
        in 360 * DAY..Long.MAX_VALUE -> "более года назад"
        else -> "undefined interval"
    }
}
