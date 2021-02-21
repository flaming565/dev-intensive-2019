package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16): String {
    val trimStr = this.trimEnd()
    return if (trimStr.length > count) trimStr.substring(0, count).trimEnd() + "..." else trimStr
}

fun String.stripHtml(): String {
    return replace("<[^>]*>".toRegex(), "")
        .replace("&[a-z]*;".toRegex(), "")
        .replace("['\"]".toRegex(), "")
        .replace("\\s+".toRegex(), " ")
}