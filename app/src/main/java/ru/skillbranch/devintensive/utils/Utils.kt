package ru.skillbranch.devintensive.utils


object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName.isNullOrEmpty() || fullName.trim().isEmpty())
            return null to null
        val parts: List<String> = fullName.split(" ")
        val firstName = parts.getOrNull(0)
        val lastName = parts.getOrNull(1)

        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstInitial = firstName?.trim()?.let { if (it.isNotEmpty()) it.first().toUpperCase().toString() else null }
                ?: ""
        val secondInitial = lastName?.trim()?.let { if (it.isNotEmpty()) it.first().toUpperCase().toString() else null }
                ?: ""

        if (firstInitial.isNullOrEmpty() && secondInitial.isNullOrEmpty())
            return null

        return firstInitial + secondInitial
    }

    private val transDictionary: Map<String, String> = mapOf(
            "а" to "a", "б" to "b", "в" to "v", "г" to "g", "д" to "d",
            "е" to "e", "ё" to "e", "ж" to "zh", "з" to "z", "и" to "i",
            "й" to "i", "к" to "k", "л" to "l", "м" to "m", "н" to "n",
            "о" to "o", "п" to "p", "р" to "r", "с" to "s", "т" to "t",
            "у" to "u", "ф" to "f", "х" to "h", "ц" to "c", "ч" to "ch",
            "ш" to "sh", "щ" to "sh'", "ъ" to "", "ы" to "i", "ь" to "",
            "э" to "e", "ю" to "yu", "я" to "ya"
    )

    fun transliteration(payload: String, divider: String = " "): String {
        return payload.map { char ->
            when (char) {
                in 'A'..'Z', in 'a'..'z' -> char.toString()
                in 'А'..'Я' -> transDictionary[char.toLowerCase().toString()]?.capitalize()
                in 'а'..'я' -> transDictionary[char.toLowerCase().toString()]
                ' ' -> divider
                else -> char.toString()
            }
        }.joinToString("")
    }
}