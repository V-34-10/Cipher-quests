package com.ciphero.questa.ui.games.findpair.image

fun List<Int>.repeat(repeatTimes: Int): List<Int> {
    val result = mutableListOf<Int>()
    repeat(repeatTimes) {
        result.addAll(this)
    }
    return result
}