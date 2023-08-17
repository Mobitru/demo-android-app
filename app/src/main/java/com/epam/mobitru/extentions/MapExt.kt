package com.epam.mobitru.extentions

fun <K, V> Map<K, V>.getKey(target: V): K {
    return this.keys.first { target == this[it] }
}