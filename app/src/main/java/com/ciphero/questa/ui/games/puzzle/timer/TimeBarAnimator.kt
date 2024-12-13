package com.ciphero.questa.ui.games.puzzle.timer

import android.animation.ValueAnimator
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ciphero.questa.databinding.FragmentPuzzleGameBinding
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogLoseGameFindPair
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogLoseGamePuzzle
import com.ciphero.questa.ui.games.findpair.controller.ControllerFindPairGame
import com.ciphero.questa.utils.AnimatorManager.resetLineTimer

class TimeBarAnimator(
    private val gameManager: ControllerFindPairGame?,
    private val binding: ViewBinding?
) {
    private var timer: CountDownTimer? = null
    private var startTime = 0L
    private var elapsedTime = 0L
    private val animationDuration = 30_000L
    private var initialWidth = 0
    private var fragment: Fragment? = null
    private var valueAnimator: ValueAnimator? = null

    fun startTimer(fragment: Fragment) {
        this.fragment = fragment
        if (initialWidth == 0) {
            when (binding) {
                is FragmentPuzzleGameBinding -> initialWidth =
                    binding.timerProgressBar.lineTimer.width
            }
        }
        startTime = System.currentTimeMillis() - elapsedTime

        timer = object : CountDownTimer(animationDuration, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime = System.currentTimeMillis() - startTime
                when (binding) {
                    is FragmentPuzzleGameBinding -> animateLineTimer(
                        binding,
                        millisUntilFinished
                    )
                }
            }

            override fun onFinish() {
                fragment.let { frag ->
                    if (frag.isAdded) {
                        when (binding) {
                            is FragmentPuzzleGameBinding -> startDialogLoseGamePuzzle(frag)
                            else -> startDialogLoseGameFindPair(
                                frag,
                                gameManager
                            )
                        }
                    }
                }
            }
        }.start()
    }

    private fun animateLineTimer(
        binding: FragmentPuzzleGameBinding,
        millisUntilFinished: Long
    ) {
        val newWidth = (millisUntilFinished / animationDuration.toFloat() * initialWidth).toInt()
        valueAnimator?.cancel()
        valueAnimator =
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

    fun stopTimer(binding: ViewBinding) {
        timer?.cancel()
        valueAnimator?.cancel()
        when (binding) {
            is FragmentPuzzleGameBinding -> resetLineTimer(binding)
        }
        startTime = 0
        elapsedTime = 0
    }
}