package com.ciphero.questa.ui.games.findpair.observer

import android.os.Handler
import android.os.Looper
import android.view.View
import com.ciphero.questa.databinding.FragmentFindPairGameBinding
import com.ciphero.questa.model.FindPair
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogVictoryGameFindPair
import com.ciphero.questa.ui.games.findpair.FindPairGameFragment
import com.ciphero.questa.adapters.findpairs.InitializationAdapter
import com.ciphero.questa.ui.games.findpair.image.ItemManager
import com.ciphero.questa.ui.games.findpair.controller.ControllerFindPairGame

class MatchChecker(
    private val initializationAdapter: InitializationAdapter,
    private val itemManager: ItemManager
) {
    fun checkMatch(
        binding: FragmentFindPairGameBinding,
        first: FindPair,
        second: FindPair,
        gameManager: ControllerFindPairGame,
        onReset: () -> Unit
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (first.img == second.img) markAsMatched(first, second)
            else resetCards(first, second)

            updateGameState(binding, gameManager, onReset)
        }, 1000L)
    }

    private fun markAsMatched(first: FindPair, second: FindPair) {
        first.match = true
        second.match = true
    }

    private fun resetCards(first: FindPair, second: FindPair) {
        first.flip = false
        second.flip = false
        initializationAdapter.notifyItemChanged(first.id)
        initializationAdapter.notifyItemChanged(second.id)
    }

    private fun updateGameState(
        binding: FragmentFindPairGameBinding,
        gameManager: ControllerFindPairGame,
        onReset: () -> Unit
    ) {
        if (itemManager.getPairList().all { it.match }) {
            binding.btnOkay.visibility = View.VISIBLE
            binding.btnOkay.setOnClickListener {
                startDialogVictoryGameFindPair(FindPairGameFragment(), gameManager)
            }
        }
        onReset()
    }
}