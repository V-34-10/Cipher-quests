package com.ciphero.questa.model

data class FindPair(
    val img: Int,
    var flip: Boolean = false,
    var match: Boolean = false,
    var id: Int = -1
)