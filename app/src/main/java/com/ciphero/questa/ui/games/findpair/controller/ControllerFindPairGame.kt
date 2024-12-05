package com.ciphero.questa.ui.games.findpair.controller

import android.view.View
import com.ciphero.questa.adapters.findpairs.InitializationAdapter
import com.ciphero.questa.databinding.FragmentFindPairGameBinding
import com.ciphero.questa.ui.games.findpair.FindPairGameFragment
import com.ciphero.questa.ui.games.findpair.image.ItemManager
import com.ciphero.questa.ui.games.findpair.observer.ObserveClickPair
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator

class ControllerFindPairGame(
    private val binding: FragmentFindPairGameBinding,
    fragment: FindPairGameFragment
) {
    private val timer = TimeBarAnimator(this, binding)
    private val adapter = InitializationAdapter(binding)
    private val itemManager = ItemManager()
    private val clickHandler = ObserveClickPair(adapter, itemManager, timer, fragment, binding)

    init {
        itemManager.setupPairItems()
        adapter.setAdapter(itemManager.getPairList())
        clickHandler.setupClickListener(this)
    }

    fun stopGame() {
        resetGame()
        timer.stopTimer(binding)
        itemManager.resetPairs()
        itemManager.setupPairItems()
        adapter.resetAdapter()
    }

    private fun resetGame() {
        clickHandler.stepSearchPair = 0
        clickHandler.isGameStarted = false
        binding.btnOkay.visibility = View.GONE
    }
}