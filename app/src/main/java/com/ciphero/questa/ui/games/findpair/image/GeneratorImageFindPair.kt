package com.ciphero.questa.ui.games.findpair.image

import com.ciphero.questa.R

object GeneratorImageFindPair {
    private val gridConfig = GridFindPair(
        img = listOf(
            R.drawable.find_1,
            R.drawable.find_2,
            R.drawable.find_3,
            R.drawable.find_4,
            R.drawable.find_5
        ),
        row = 3,
        col = 3
    )

    fun generateImageGrid(): List<Int> =
        gridConfig.img.take((gridConfig.row * gridConfig.col) / 2).flatMap { listOf(it, it) }
            .shuffled()
}