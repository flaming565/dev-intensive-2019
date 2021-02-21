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

    fun plural(value: Int): String {
        val isLessFive: Boolean = value % 10 in 2..4
        val unitStr = when (this) {
            SECOND -> if (value % 10 == 1 && (value > 20 || value < 10)) "секунду" else if (isLessFive) "секунды" else "секунд"
            MINUTE -> if (value % 10 == 1 && (value > 20 || value < 10)) "минуту" else if (isLessFive) "минуты" else "минут"
            HOUR -> if (value % 10 == 1 && (value > 20 || value < 10)) "час" else if (isLessFive) "часа" else "часов"
            DAY -> if (value % 10 == 1 && (value > 20 || value < 10)) "день" else if (isLessFive) "дня" else "дней"
        }
        return "$value $unitStr"
    }
}

fun Date.humanizeDiff(date: Date = Date()): String {
    return when (val value = (date.time - this.time)) {
        in Long.MIN_VALUE..-360 * DAY - 1 -> "более чем через год"
        in -360 * DAY..-26 * HOUR - 1 -> "через ${TimeUnits.DAY.plural((-value / DAY).toInt())}"
        in -26 * HOUR..-22 * HOUR - 1 -> "через день"
        in -22 * HOUR..-75 * MINUTE - 1 -> "через ${TimeUnits.HOUR.plural((-value / HOUR).toInt())}"
        in -75 * MINUTE..-45 * MINUTE - 1 -> "через час"
        in -45 * MINUTE..-76 * SECOND -> "через ${TimeUnits.MINUTE.plural((-value / MINUTE).toInt())}"
        in -75 * SECOND..-46 * SECOND -> "через минуту"
        in -45 * SECOND..-2 * SECOND -> "через несколько секунд"
        in -1 * SECOND..1 * SECOND -> "только что"
        in 2 * SECOND..45 * SECOND -> "несколько секунд назад"
        in 46 * SECOND..75 * SECOND -> "минуту назад"
        in 76..45 * MINUTE -> "${TimeUnits.MINUTE.plural((value / MINUTE).toInt())} назад"
        in 45 * MINUTE + 1..75 * MINUTE -> "час назад"
        in 75 * MINUTE + 1..22 * HOUR -> "${TimeUnits.HOUR.plural((value / HOUR).toInt())} назад"
        in 22 * HOUR + 1..26 * HOUR -> "день назад"
        in 26 * HOUR + 1..360 * DAY -> "${TimeUnits.DAY.plural((value / DAY).toInt())} назад"
        in 360 * DAY + 1..Long.MAX_VALUE -> "более года назад"
        else -> "undefined interval"
    }
}
