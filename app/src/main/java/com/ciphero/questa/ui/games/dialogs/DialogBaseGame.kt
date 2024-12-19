package com.ciphero.questa.ui.games.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ciphero.questa.R
import com.ciphero.questa.ui.games.findpair.controller.ControllerFindPairGame
import com.ciphero.questa.ui.games.puzzle.controller.GovernGamePuzzle.restartRound
import com.ciphero.questa.utils.AnimatorManager.startAnimateClickButton
import com.ciphero.questa.utils.DecoratorNavigationUI
import com.ciphero.questa.utils.DecoratorNavigationUI.navigateToWithFragment

object DialogsBaseGame {

    private fun createDialog(context: Context, layoutResId: Int): Dialog {
        return Dialog(context, R.style.DialogTheme).apply {
            setContentView(layoutResId)

            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    private fun showDialog(
        activity: Activity,
        fragment: Fragment,
        layoutResId: Int,
        onDismiss: () -> Unit?
    ) {
        val dialog = createDialog(fragment.requireContext(), layoutResId)
        dialog.show()
        Handler(Looper.getMainLooper()).postDelayed({
            DecoratorNavigationUI.hideNavigationBar(activity)
        }, 100)
        dialog.findViewById<View>(R.id.btn_next)?.setOnClickListener {
            startAnimateClickButton(it, fragment.requireContext())
            dialog.dismiss()
            onDismiss()
        }
        dialog.findViewById<View>(R.id.btn_exit)?.setOnClickListener {
            startAnimateClickButton(it, fragment.requireContext())
            dialog.dismiss()
            navigateToWithFragment(fragment)
        }
    }

    fun startDialogLoseGameFindPair(
        fragment: Fragment,
        gameManager: ControllerFindPairGame?
    ) =
        showDialog(
            fragment.requireActivity(),
            fragment, R.layout.dialog_game_find_pair_lose
        ) { gameManager?.stopGame() }

    fun startDialogPauseGameFindPair(
        fragment: Fragment,
        gameManager: ControllerFindPairGame?
    ) =
        showDialog(
            fragment.requireActivity(),
            fragment, R.layout.dialog_game_find_pair_pause
        ) { gameManager?.stopGame() }

    fun startDialogVictoryGameFindPair(
        fragment: Fragment,
        gameManager: ControllerFindPairGame?
    ) =
        showDialog(
            fragment.requireActivity(),
            fragment, R.layout.dialog_game_find_pair_victory
        ) { gameManager?.stopGame() }

    fun startDialogLoseGamePuzzle(fragment: Fragment) = showDialog(
        fragment.requireActivity(),
        fragment, R.layout.dialog_game_lose
    ) { restartRound() }

    fun startDialogPauseGamePuzzle(fragment: Fragment) = showDialog(
        fragment.requireActivity(),
        fragment, R.layout.dialog_game_pause
    ) { restartRound() }

    fun startDialogVictoryGamePuzzle(fragment: Fragment) = showDialog(
        fragment.requireActivity(),
        fragment, R.layout.dialog_game_victory
    ) { restartRound() }

    fun startDialogLoseGameQuiz(fragment: Fragment, onDismiss: () -> Unit) =
        showDialog(fragment.requireActivity(), fragment, R.layout.dialog_game_find_pair_lose, onDismiss)

    fun startDialogVictoryGameQuiz(fragment: Fragment, onDismiss: () -> Unit) =
        showDialog(fragment.requireActivity(), fragment, R.layout.dialog_game_find_pair_victory, onDismiss)
}