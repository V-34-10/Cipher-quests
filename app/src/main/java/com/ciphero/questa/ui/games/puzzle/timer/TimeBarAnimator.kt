package com.ciphero.questa.ui.games.puzzle.timer

import android.animation.ValueAnimator
import android.os.CountDownTimer
import androidx.viewbinding.ViewBinding
import com.ciphero.questa.databinding.FragmentPuzzleGameBinding
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogLoseGameFindPair
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogLoseGamePuzzle
import com.ciphero.questa.ui.games.findpair.CardGameManager
import com.ciphero.questa.ui.games.findpair.FindPairGameFragment
import com.ciphero.questa.ui.games.puzzle.PuzzleGameFragment

class TimeBarAnimator(
    private val gameManager: CardGameManager?
) {
    private var timer: CountDownTimer? = null
    private var startTime = 0L
    private var elapsedTime = 0L
    private val animationDuration = 30_000L
    private var initialWidth = 0

    fun startTimer(binding: ViewBinding) {
        if (initialWidth == 0) {
            when (binding) {
                is FragmentPuzzleGameBinding -> initialWidth = binding.timerProgressBar.lineTimer.width
            }
        }
        startTime = System.currentTimeMillis() - elapsedTime

        timer = object : CountDownTimer(animationDuration, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime = System.currentTimeMillis() - startTime
                when (binding) {
                    is FragmentPuzzleGameBinding ->  updateLineTimerWidth(binding, millisUntilFinished)
                }
            }

            override fun onFinish() {
                when (binding) {
                    is FragmentPuzzleGameBinding ->
                        startDialogLoseGamePuzzle(PuzzleGameFragment())

                    else -> startDialogLoseGameFindPair(
                        FindPairGameFragment(),
                        gameManager
                    )
                }

            }
        }.start()
    }

    private fun updateLineTimerWidth(
        binding: FragmentPuzzleGameBinding,
        millisUntilFinished: Long
    ) {
        val newWidth = (millisUntilFinished / animationDuration.toFloat() * initialWidth).toInt()
        ValueAnimator.ofInt(binding.timerProgressBar.lineTimer.width, newWidth).apply {
            duration = 1000L
            addUpdateListener {
                val value = it.animatedValue as Int
                val layoutParams = binding.timerProgressBar.lineTimer.layoutParams
                layoutParams.width = value
                binding.timerProgressBar.lineTimer.layoutParams = layoutParams
            }
            start()
        }
    }

    fun stopTimer(binding: FragmentPuzzleGameBinding) {
        timer?.cancel()
        resetTimer(binding)
    }

    private fun resetTimer(binding: FragmentPuzzleGameBinding) {
        startTime = 0
        elapsedTime = 0
        resetLineTimerWidth(binding)
    }

    private fun resetLineTimerWidth(binding: FragmentPuzzleGameBinding) {
        val layoutParams = binding.timerProgressBar.lineTimer.layoutParams
        layoutParams.width = initialWidth
        binding.timerProgressBar.lineTimer.layoutParams = layoutParams
    }
}