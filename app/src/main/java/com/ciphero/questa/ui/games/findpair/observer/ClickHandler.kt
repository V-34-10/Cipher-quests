package com.ciphero.questa.ui.games.findpair.observer

import com.ciphero.questa.databinding.FragmentFindPairGameBinding
import com.ciphero.questa.model.FindPair
import com.ciphero.questa.adapters.findpairs.InitializationAdapter
import com.ciphero.questa.ui.games.findpair.image.ItemManager
import com.ciphero.questa.ui.games.findpair.controller.ControllerFindPairGame
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator

class ClickHandler(
    private val initializationAdapter: InitializationAdapter,
    private val itemManager: ItemManager,
    private val timer: TimeBarAnimator
) {
    private var firstSelected: FindPair? = null
    private var secondSelected: FindPair? = null
    private var isComparing = false
    var isGameStarted = false

    fun setupClickListener(
        binding: FragmentFindPairGameBinding,
        gameManager: ControllerFindPairGame
    ) {
        initializationAdapter.setItemClickListener { pairItem, position ->
            handleItemClick(binding, pairItem, position, gameManager)
        }
    }

    private fun handleItemClick(
        binding: FragmentFindPairGameBinding,
        pair: FindPair,
        position: Int,
        gameManager: ControllerFindPairGame
    ) {
        if (!isValidClick(pair)) return
        if (!isGameStarted) startGame(binding)
        flipCard(pair, position)

        firstSelected = firstSelected ?: pair
        if (firstSelected != pair) {
            secondSelected = pair
            isComparing = true
            MatchChecker(initializationAdapter, itemManager).checkMatch(
                binding, firstSelected!!, secondSelected!!, gameManager
            ) { resetSelection() }
        }
    }

    private fun isValidClick(pair: FindPair): Boolean = !isComparing && !pair.flip && !pair.match

    private fun flipCard(pair: FindPair, position: Int) {
        pair.apply { flip = true; id = position }
        initializationAdapter.notifyItemChanged(position)
    }

    private fun startGame(binding: FragmentFindPairGameBinding) {
        timer.startTimer(binding)
        isGameStarted = true
    }

    private fun resetSelection() {
        firstSelected = null
        secondSelected = null
        isComparing = false
    }
}