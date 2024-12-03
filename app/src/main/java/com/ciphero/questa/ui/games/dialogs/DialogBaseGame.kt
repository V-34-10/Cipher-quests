package com.ciphero.questa.ui.games.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.ciphero.questa.R
import com.ciphero.questa.ui.games.puzzle.GovernGamePuzzle.restartRound
import com.ciphero.questa.ui.menu.MenuActivity

object DialogsBaseGame {

    private fun createDialog(context: Context, layoutResId: Int): Dialog {
        val dialog = Dialog(context, R.style.DialogTheme)
        dialog.setContentView(layoutResId)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    private fun startDialog(
        context: Context,
        layoutResId: Int,
        buttonIdNext: Int,
        buttonIdExit: Int,
        onDismiss: () -> Unit,
        onExit: () -> Unit = {}
    ) {
        val dialog = createDialog(context, layoutResId)
        val buttonNext = dialog.findViewById<ImageView>(buttonIdNext)
        val buttonExit = dialog.findViewById<ImageView>(buttonIdExit)
        buttonNext.setOnClickListener {
            dialog.dismiss()
            onDismiss()
        }
        buttonExit.setOnClickListener {
            dialog.dismiss()
            onExit()
        }
        dialog.show()
    }

    /*fun startDialogLoseGameFindPair(context: Context, gameManager: CardGameManager?) {
        startDialog(context, R.layout.dialog_game_lose, R.id.btn_restart_dialog) {
            gameManager?.stopGame()
        }
    }

    fun startDialogVictoryGameFindPair(
        context: Context,
        gameManager: CardGameManager?,
        bindingSetup: BindingSetup?
    ) {
        startDialog(context, R.layout.dialog_game_victory, R.id.btn_next_dialog) {
            gameManager?.stopGame()
            bindingSetup?.observeButtonBonusGame(context)
        }
    }*/

    fun startDialogLoseGamePuzzle(
        context: Context,
        fragment: Fragment
    ) {
        startDialog(
            context, R.layout.dialog_game_lose, R.id.btn_exit, R.id.btn_next,
            onDismiss = { restartRound() },
            onExit = {
                val intent = Intent(fragment.requireContext(), MenuActivity::class.java)
                fragment.startActivity(intent)
                fragment.requireActivity().finish()
            }
        )
    }

    fun startDialogPauseGamePuzzle(
        context: Context,
        fragment: Fragment
    ) {
        startDialog(
            context, R.layout.dialog_game_pause, R.id.btn_exit, R.id.btn_next,
            onDismiss = { restartRound() },
            onExit = {
                val intent = Intent(fragment.requireContext(), MenuActivity::class.java)
                fragment.startActivity(intent)
                fragment.requireActivity().finish()
            }
        )
    }

    fun startDialogVictoryGamePuzzle(
        context: Context,
        fragment: Fragment
    ) {
        startDialog(context, R.layout.dialog_game_victory, R.id.btn_next, R.id.btn_exit,
            onDismiss = { restartRound() },
            onExit = {
                val intent = Intent(fragment.requireContext(), MenuActivity::class.java)
                fragment.startActivity(intent)
                fragment.requireActivity().finish()
            }
        )
    }
}