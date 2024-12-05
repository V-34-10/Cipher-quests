package com.ciphero.questa.ui.games.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.ciphero.questa.R
import com.ciphero.questa.ui.games.findpair.controller.ControllerFindPairGame
import com.ciphero.questa.ui.games.puzzle.controller.GovernGamePuzzle.restartRound
import com.ciphero.questa.ui.menu.MenuActivity

object DialogsBaseGame {

    private fun createDialog(context: Context, layoutResId: Int): Dialog {
        return Dialog(context, R.style.DialogTheme).apply {
            setContentView(layoutResId)

            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            window?.let { window ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.setDecorFitsSystemWindows(false)
                    val controller = window.insetsController
                    controller?.let {
                        it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                        it.systemBarsBehavior =
                            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                } else {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    )
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = Color.TRANSPARENT
                }
            }
        }
    }

    private fun showDialog(
        fragment: Fragment,
        layoutResId: Int,
        onDismiss: () -> Unit
    ) {
        val dialog = createDialog(fragment.requireContext(), layoutResId)
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

    fun startDialogLoseGameFindPair(fragment: Fragment, gameManager: ControllerFindPairGame?) =
        showDialog(
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