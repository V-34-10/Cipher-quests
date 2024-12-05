package com.ciphero.questa.ui.games.puzzle.image

import android.annotation.SuppressLint
import android.content.Context
import com.ciphero.questa.R
import com.ciphero.questa.model.Puzzle

class GeneratorPuzzleImage(private val context: Context) {
    private fun resIdForName(name: String): Int =
        context.resources.getIdentifier(name, "drawable", context.packageName)

    val gridConfig = GridPuzzle(
        victoryListPuzzle = List(9) { resIdForName("puzzle$it") },
        spanGrid = 3,
        idEndPuzzle = 8
    )

    @SuppressLint("DiscouragedApi")
    fun preparationPuzzles(): MutableList<Puzzle> {
        val initialPuzzles = createInitialPuzzles(gridConfig)
        initialPuzzles.shuffle()
        addFinalPuzzle(initialPuzzles, gridConfig)
        return initialPuzzles
    }

    private fun createInitialPuzzles(levelConfig: GridPuzzle): MutableList<Puzzle> =
        levelConfig.victoryListPuzzle.dropLast(1)
            .mapIndexed { index, resourceId -> Puzzle(resourceId, index) }.toMutableList()

    private fun addFinalPuzzle(puzzles: MutableList<Puzzle>, levelConfig: GridPuzzle) =
        puzzles.add(Puzzle(R.drawable.puzzle8, levelConfig.idEndPuzzle))
}