package com.ciphero.questa.ui.games.puzzle.image

import android.annotation.SuppressLint
import android.content.Context
import com.ciphero.questa.R
import com.ciphero.questa.model.Puzzle
import java.util.Collections

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
        return generateShuffledPuzzle(initialPuzzles, gridConfig.spanGrid)
    }

    private fun createInitialPuzzles(levelConfig: GridPuzzle): MutableList<Puzzle> =
        levelConfig.victoryListPuzzle
            .mapIndexed { index, resourceId -> Puzzle(resourceId, index) }.toMutableList()

    private fun generateShuffledPuzzle(initialPuzzles: MutableList<Puzzle>, gridSize: Int): MutableList<Puzzle> {
        /*// Створюємо копію зібраного пазла
        val currentPuzzles = initialPuzzles.toMutableList()
        // Знаходимо порожню плитку (спочатку в правому нижньому куті)
        var emptyTileIndex = currentPuzzles.indexOfFirst { it.id == gridConfig.idEndPuzzle }
        // Емулюємо послідовність ходів для перемішування
        repeat(100) { // Кількість ходів для перемішування
            val possibleMoves = getPossibleMoves(emptyTileIndex, gridSize)
            val moveToIndex = possibleMoves.random()
            // Обмін порожньої плитки з вибраною
            currentPuzzles.swap(emptyTileIndex, moveToIndex)
            // Оновлюємо індекс порожньої плитки
            emptyTileIndex = moveToIndex
            gridConfig.idEndPuzzle = emptyTileIndex
        }
        return currentPuzzles*/

        val puzzles = initialPuzzles.toMutableList()
        var emptyTileIndex = puzzles.size - 1

        val shuffleMoves = 1000
        repeat(shuffleMoves) {
            val possibleMoves = getPossibleMoves(emptyTileIndex, gridSize)

            if (possibleMoves.isEmpty()) {
                // If no possible moves, reshuffle.  Less likely with increased shuffleMoves.
                puzzles.shuffle()
                emptyTileIndex = puzzles.indexOfFirst { it.img == R.drawable.puzzle8 }
            } else {
                val moveToIndex = possibleMoves.random()
                Collections.swap(puzzles, emptyTileIndex, moveToIndex)
                emptyTileIndex = moveToIndex
            }
        }

        // Ensure empty tile is at the end
        val emptyTile = puzzles.removeAt(emptyTileIndex)
        puzzles.add(emptyTile)
        return puzzles
    }

    private fun getPossibleMoves(emptyTileIndex: Int, gridSize: Int): List<Int> {
        val possibleMoves = mutableListOf<Int>()

        val row = emptyTileIndex / gridSize
        val col = emptyTileIndex % gridSize

        if (row > 0) possibleMoves.add(emptyTileIndex - gridSize)
        if (row < gridSize - 1) possibleMoves.add(emptyTileIndex + gridSize)
        if (col > 0) possibleMoves.add(emptyTileIndex - 1)
        if (col < gridSize - 1) possibleMoves.add(emptyTileIndex + 1)

        return possibleMoves
    }

    private fun MutableList<Puzzle>.swap(index1: Int, index2: Int) {
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }
}