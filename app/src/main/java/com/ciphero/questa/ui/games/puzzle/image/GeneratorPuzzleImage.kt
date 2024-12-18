package com.ciphero.questa.ui.games.puzzle.image

import android.annotation.SuppressLint
import android.content.Context
import com.ciphero.questa.R
import com.ciphero.questa.model.Puzzle

class GeneratorPuzzleImage(context: Context) {
    private val resources = context.resources
    private val packageName = context.packageName

    val gridConfig = GridPuzzle(
        victoryListPuzzle = generatePuzzleResources(),
        spanGrid = 3,
        idEndPuzzle = 8
    )

    private fun generatePuzzleResources(): List<Int> =
        List(9) { index -> resources.getIdentifier("puzzle$index", "drawable", packageName) }

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