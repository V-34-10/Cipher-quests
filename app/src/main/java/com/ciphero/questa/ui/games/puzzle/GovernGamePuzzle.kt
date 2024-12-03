package com.ciphero.questa.ui.games.puzzle

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.ciphero.questa.R
import com.ciphero.questa.adapters.puzzle.PuzzleGameAdapter
import com.ciphero.questa.databinding.FragmentPuzzleGameBinding
import com.ciphero.questa.model.Puzzle
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator
import com.ciphero.questa.ui.games.puzzle.image.GeneratorPuzzleImage

@SuppressLint("StaticFieldLeak")
object GovernGamePuzzle {
    private lateinit var puzzleAdapter: PuzzleGameAdapter
    private lateinit var timer: TimeBarAnimator
    private lateinit var binding: FragmentPuzzleGameBinding
    private lateinit var context: Context
    var moveCount = 0

    fun initGovernGamePuzzle(
        binding: FragmentPuzzleGameBinding,
        context: Context
    ) {
        this.binding = binding
        this.context = context
        timer = TimeBarAnimator(null)
    }

    fun startRound() {
        setAdapter(binding, context)
    }

    fun restartRound() {
        moveCount = 0
        timer.stopTimer(binding)
        setAdapter(binding, context)
    }

    @SuppressLint("DiscouragedApi")
    private fun getShuffledPuzzles(context: Context): MutableList<Puzzle> {
        val puzzleImageSetup = GeneratorPuzzleImage(context)
        val levelConfig = puzzleImageSetup.gridConfig

        val resourcesPuzzles = levelConfig.victoryListPuzzle.dropLast(1)
        val sectorPuzzle =
            resourcesPuzzles.mapIndexed { index, resourceId -> Puzzle(resourceId, index) }
                .toMutableList()

        sectorPuzzle.shuffle()
        sectorPuzzle.add(
            Puzzle(
                R.drawable.puzzle_0,
                levelConfig.idEndPuzzle
            )
        )
        return sectorPuzzle
    }

    private fun setAdapter(
        binding: FragmentPuzzleGameBinding,
        context: Context
    ) {
        val puzzleImageSetup = GeneratorPuzzleImage(context)
        val levelConfig = puzzleImageSetup.gridConfig
        puzzleAdapter = PuzzleGameAdapter(
            binding.sceneCard,
            getShuffledPuzzles(context),
            context,
            timer,
            binding
        )
        binding.sceneCard.layoutManager = GridLayoutManager(context, levelConfig.spanGrid)
        binding.sceneCard.adapter = puzzleAdapter
    }
}