package com.ciphero.questa.ui.games.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.fragment.app.Fragment
import com.ciphero.questa.R
import com.ciphero.questa.ui.games.puzzle.controller.GovernGamePuzzle.restartRound
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.ui.games.findpair.controller.ControllerFindPairGame

object DialogsBaseGame {

    private fun createDialog(context: Context, layoutResId: Int) =
        Dialog(context, R.style.DialogTheme).apply {
            setContentView(layoutResId)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
        }

    private fun showDialog(
        fragment: Fragment,
        layoutResId: Int,
        onDismiss: () -> Unit
    ) {
        val dialog = createDialog(fragment.requireContext(), layoutResId)
        dialog.findViewById<View>(R.id.btn_continue)?.setOnClickListener {
            dialog.dismiss()
            onDismiss()
        }
        dialog.findViewById<View>(R.id.btn_next)?.setOnClickListener {
            dialog.dismiss()
            onDismiss()
        }
        dialog.findViewById<View>(R.id.btn_exit)?.setOnClickListener {
            dialog.dismiss()
            navigateToMenu(fragment)
        }
        dialog.show()
    }

    fun startDialogLoseGameFindPair(fragment: Fragment, gameManager: ControllerFindPairGame?) = showDialog(
        fragment, R.layout.dialog_game_find_pair_lose
    ) { gameManager?.stopGame() }

    fun startDialogPauseGameFindPair(fragment: Fragment, gameManager: ControllerFindPairGame?) =
        showDialog(
            fragment, R.layout.dialog_game_find_pair_pause
        ) { gameManager?.stopGame() }

    fun startDialogVictoryGameFindPair(fragment: Fragment, gameManager: ControllerFindPairGame?) =
        showDialog(
            fragment, R.layout.dialog_game_find_pair_victory
        ) { gameManager?.stopGame() }

    fun startDialogLoseGamePuzzle(fragment: Fragment) = showDialog(
        fragment, R.layout.dialog_game_lose
    ) { restartRound() }

    fun startDialogPauseGamePuzzle(fragment: Fragment) = showDialog(
        fragment, R.layout.dialog_game_pause
    ) { restartRound() }

    fun startDialogVictoryGamePuzzle(fragment: Fragment) = showDialog(
        fragment, R.layout.dialog_game_victory
    ) { restartRound() }

    private fun navigateToMenu(fragment: Fragment) {
        val intent = Intent(fragment.requireContext(), MenuActivity::class.java)
        fragment.startActivity(intent)
        fragment.requireActivity().finish()
    }
}