package com.ciphero.questa.ui.games.puzzle.timer

import android.animation.ValueAnimator
import android.os.CountDownTimer
import com.ciphero.questa.databinding.FragmentPuzzleGameBinding
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogLoseGameFindPair
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogLoseGamePuzzle
import com.ciphero.questa.ui.games.findpair.CardGameManager
import com.ciphero.questa.ui.games.findpair.FindPairGameFragment
import com.ciphero.questa.ui.games.puzzle.PuzzleGameFragment

class TimeBarAnimator(
    private val binding: FragmentPuzzleGameBinding,
    private val gameManager: CardGameManager?
) {
    private var timer: CountDownTimer? = null
    private var startTime = 0L
    private var elapsedTime = 0L
    private val animationDuration = 30_000L
    private var initialWidth = 0

    private val lineTimer get() = binding.timerProgressBar.lineTimer

    fun startTimer() {
        if (initialWidth == 0) {
            initialWidth = lineTimer.width
        }
        startTime = System.currentTimeMillis() - elapsedTime

        timer = object : CountDownTimer(animationDuration, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime = System.currentTimeMillis() - startTime
                updateLineTimerWidth(millisUntilFinished)
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

    private fun updateLineTimerWidth(millisUntilFinished: Long) {
        val newWidth = (millisUntilFinished / animationDuration.toFloat() * initialWidth).toInt()
        ValueAnimator.ofInt(lineTimer.width, newWidth).apply {
            duration = 1000L
            addUpdateListener {
                val value = it.animatedValue as Int
                val layoutParams = lineTimer.layoutParams
                layoutParams.width = value
                lineTimer.layoutParams = layoutParams
            }
            start()
        }
    }

    fun stopTimer() {
        timer?.cancel()
        resetTimer()
    }

    private fun resetTimer() {
        startTime = 0
        elapsedTime = 0
        resetLineTimerWidth()
    }

    private fun resetLineTimerWidth() {
        val layoutParams = lineTimer.layoutParams
        layoutParams.width = initialWidth
        lineTimer.layoutParams = layoutParams
    }
}