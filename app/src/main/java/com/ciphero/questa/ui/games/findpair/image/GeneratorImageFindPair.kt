package com.ciphero.questa.ui.games.findpair.image

import com.ciphero.questa.R

object GeneratorImageFindPair {
    private val gridFindPair = GridFindPair(
        img = listOf(
            R.drawable.find_1,
            R.drawable.find_2,
            R.drawable.find_3,
            R.drawable.find_4,
            R.drawable.find_5,
            R.drawable.find_6
        ),
        row = 3,
        col = 3
    )

    fun getImages(): List<Int> =
        gridFindPair.img.repeat(gridFindPair.repeat).take(gridFindPair.row * gridFindPair.col)
}