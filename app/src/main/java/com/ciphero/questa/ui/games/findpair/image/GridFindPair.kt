package com.ciphero.questa.ui.games.findpair.image

data class GridFindPair(
    val img: List<Int>,
    val row: Int,
    val col: Int,
    val repeat: Int = 2
)