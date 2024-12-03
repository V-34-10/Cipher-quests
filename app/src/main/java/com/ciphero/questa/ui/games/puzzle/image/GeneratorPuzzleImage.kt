package com.ciphero.questa.ui.games.puzzle.image

import android.content.Context

data class LevelPuzzleConfig(
    val victoryListPuzzle: List<Int>,
    val spanGrid: Int,
    val idEndPuzzle: Int
)

class GeneratorPuzzleImage(private val context: Context) {
    private fun resIdForName(name: String): Int =
        context.resources.getIdentifier(name, "drawable", context.packageName)

    private val easyPuzzleList = List(9) { resIdForName("puzzle_$it") }
    val levelConfig = LevelPuzzleConfig(
        victoryListPuzzle = easyPuzzleList,
        spanGrid = 3,
        idEndPuzzle = 0
    )
}