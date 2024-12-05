package com.ciphero.questa.ui.games.puzzle.controller

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.ciphero.questa.adapters.puzzle.PuzzleGameAdapter
import com.ciphero.questa.databinding.FragmentPuzzleGameBinding
import com.ciphero.questa.ui.games.puzzle.PuzzleGameFragment
import com.ciphero.questa.ui.games.puzzle.image.GeneratorPuzzleImage
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator

@SuppressLint("StaticFieldLeak")
object GovernGamePuzzle {
    private lateinit var puzzleAdapter: PuzzleGameAdapter
    private lateinit var timer: TimeBarAnimator
    private lateinit var binding: FragmentPuzzleGameBinding
    private lateinit var context: Context
    private lateinit var fragment: PuzzleGameFragment
    var moveCount = 0

    fun initGovernGamePuzzle(
        binding: FragmentPuzzleGameBinding,
        context: Context,
        fragment: PuzzleGameFragment,
        timer: TimeBarAnimator
    ) {
        GovernGamePuzzle.binding = binding
        GovernGamePuzzle.context = context
        GovernGamePuzzle.timer = timer
        GovernGamePuzzle.fragment = fragment
    }

    fun startRound() {
        setAdapter(binding, context)
    }

    fun restartRound() {
        moveCount = 0
        timer.stopTimer(binding)
        setAdapter(binding, context)
    }

    private fun setAdapter(
        binding: FragmentPuzzleGameBinding,
        context: Context
    ) {
        val puzzleImage = GeneratorPuzzleImage(context)
        puzzleAdapter = PuzzleGameAdapter(
            binding.sceneCard,
            puzzleImage.preparationPuzzles(),
            context,
            timer,
            fragment
        )
        binding.sceneCard.layoutManager =
            GridLayoutManager(context, puzzleImage.gridConfig.spanGrid)
        binding.sceneCard.adapter = puzzleAdapter
    }
}