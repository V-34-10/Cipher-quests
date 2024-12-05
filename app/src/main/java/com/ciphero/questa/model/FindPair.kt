package com.ciphero.questa.model

data class PairData(val pair: FindPair, val position: Int)

data class FindPair(
    val img: Int,
    var flip: Boolean = false,
    var match: Boolean = false,
    var id: Int = -1
) {
    fun flip() {
        flip = !flip
    }

    fun match() {
        match = true
    }

    fun reset() {
        flip = false
        match = false
    }
}