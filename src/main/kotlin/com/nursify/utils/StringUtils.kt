package com.nursify.utils

fun Collection<String>.toCleanedStringList(): String {
    return this.toString().replace("[", "").replace("]", "")
}