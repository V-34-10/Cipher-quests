package com.ciphero.questa.ui.games.puzzle.image

import android.content.Context

class GeneratorPuzzleImage(private val context: Context) {
    private fun resIdForName(name: String): Int =
        context.resources.getIdentifier(name, "drawable", context.packageName)

    val gridConfig = GridPuzzle(
        victoryListPuzzle = List(9) { resIdForName("puzzle_$it") },
        spanGrid = 3,
        idEndPuzzle = 0
    )
}