package com.example.hgwater.util

fun String?.isJson() = this?.startsWith("{") == true && this.endsWith("}")
fun String?.isArray() = this?.startsWith("[") == true && this.endsWith("]")
