package com.ciphero.questa.utils

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ciphero.questa.databinding.FragmentFindPairGameBinding
import com.ciphero.questa.databinding.FragmentPuzzleGameBinding
import com.ciphero.questa.databinding.FragmentQuizGameBinding
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogPauseGameFindPair
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogPauseGamePuzzle
import com.ciphero.questa.ui.games.findpair.controller.ControllerFindPairGame
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.utils.AnimatorManager.startAnimateClickButton
import com.ciphero.questa.utils.DecoratorNavigationUI.navigateToActivity

object BindingManager {

    fun controlButtonGames(
        binding: ViewBinding,
        context: Context,
        fragment: Fragment,
        activity: Activity,
        timer: TimeBarAnimator? = null,
        gameGovern: ControllerFindPairGame? = null,
        resetAnswerButtonBackgrounds: () -> Unit = {},
        showNextQuestion: () -> Unit = {}
    ) {
        when (binding) {
            is FragmentPuzzleGameBinding -> {
                binding.btnPause.setOnClickListener {
                    startAnimateClickButton(it, context)
                    timer?.stopTimer(binding)
                    startDialogPauseGamePuzzle(fragment)
                }
                binding.btnBack.setOnClickListener {
                    startAnimateClickButton(it, context)
                    navigateToActivity(MenuActivity::class.java, activity)
                }
            }

            is FragmentFindPairGameBinding -> {
                binding.btnPause.setOnClickListener {
                    startAnimateClickButton(it, context)
                    gameGovern?.stopGame()
                    startDialogPauseGameFindPair(fragment, gameGovern)
                }
                binding.btnBack.setOnClickListener {
                    startAnimateClickButton(it, context)
                    gameGovern?.stopGame()
                    navigateToActivity(MenuActivity::class.java, activity)
                }
            }

            is FragmentQuizGameBinding -> {
                binding.btnNext.setOnClickListener {
                    startAnimateClickButton(it, context)
                    resetAnswerButtonBackgrounds()
                    binding.btnNext.isEnabled = false
                    binding.btnNext.visibility = View.GONE
                    showNextQuestion()
                }
                binding.btnBack.setOnClickListener {
                    startAnimateClickButton(it, context)
                    navigateToActivity(MenuActivity::class.java, activity)
                }
            }
        }
    }
}