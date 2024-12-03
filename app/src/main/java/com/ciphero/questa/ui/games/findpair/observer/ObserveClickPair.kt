package com.ciphero.questa.ui.games.findpair.observer

import com.ciphero.questa.databinding.FragmentFindPairGameBinding
import com.ciphero.questa.adapters.findpairs.InitializationAdapter
import com.ciphero.questa.ui.games.findpair.image.ItemManager
import com.ciphero.questa.ui.games.findpair.controller.ControllerFindPairGame
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator

class ObserveClickPair(
    initializationAdapter: InitializationAdapter,
    itemManager: ItemManager,
    timer: TimeBarAnimator
) {
    val clickHandler = ClickHandler(initializationAdapter, itemManager, timer)

    fun setupClickListener(
        binding: FragmentFindPairGameBinding,
        gameManager: ControllerFindPairGame
    ) {
        clickHandler.setupClickListener(binding, gameManager)
    }

    /*private var firstSelected: FindPair? = null
    private var secondSelected: FindPair? = null
    private var isComparing = false
    var isGameStarted = false

    fun setupClickListener(
        binding: FragmentFindPairGameBinding,
        gameManager: ControllerFindPairGame
    ) = initializationAdapter.setItemClickListener { pairItem, position ->
        handleClickItem(
            binding,
            pairItem,
            position,
            gameManager
        )
    }

    private fun handleClickItem(
        binding: FragmentFindPairGameBinding,
        pair: FindPair,
        position: Int,
        gameManager: ControllerFindPairGame
    ) {
        if (!isValidClick(pair)) return
        if (!isGameStarted) startGameRound(binding)
        flipCard(pair, position)

        firstSelected = firstSelected ?: pair
        if (firstSelected != pair) {
            secondSelected = pair
            isComparing = true
            checkForMatch(binding, gameManager)
        }
    }

    private fun isValidClick(pair: FindPair): Boolean = !isComparing && !pair.flip && !pair.match

    private fun flipCard(pair: FindPair, position: Int) {
        pair.apply { flip = true; id = position }
        initializationAdapter.notifyItemChanged(position)
    }

    private fun checkForMatch(
        binding: FragmentFindPairGameBinding,
        gameManager: ControllerFindPairGame
    ) = Handler(Looper.getMainLooper()).postDelayed(
        { compareCards(binding, gameManager) },
        1000L
    )

    private fun compareCards(
        binding: FragmentFindPairGameBinding,
        gameManager: ControllerFindPairGame
    ) {
        (if (firstSelected?.img == secondSelected?.img) markPairsAsMatched() else resetPairSelection())
        updateGameState(binding, gameManager)
    }

    private fun startGameRound(binding: FragmentFindPairGameBinding) {
        timer.startTimer(binding)
        isGameStarted = true
    }

    private fun updateGameState(
        binding: FragmentFindPairGameBinding,
        gameManager: ControllerFindPairGame
    ) {
        refreshDisplayedCards()
        resetSelection()

        if (itemManager.getPairList().all { it.match }) showGameVictoryDialog(binding, gameManager)
    }

    private fun showGameVictoryDialog(
        binding: FragmentFindPairGameBinding,
        gameManager: ControllerFindPairGame
    ) {
        binding.btnOkay.visibility = View.VISIBLE
        binding.btnOkay.setOnClickListener {
            startDialogVictoryGameFindPair(
                FindPairGameFragment(),
                gameManager
            )
        }
    }

    private fun refreshDisplayedCards() {
        firstSelected?.let { initializationAdapter.notifyItemChanged(it.id) }
        secondSelected?.let { initializationAdapter.notifyItemChanged(it.id) }
    }

    private fun markPairsAsMatched() {
        firstSelected?.match = true
        secondSelected?.match = true
    }

    private fun resetPairSelection() {
        firstSelected?.flip = false
        secondSelected?.flip = false
    }

    private fun resetSelection() {
        firstSelected = null
        secondSelected = null
        isComparing = false
    }*/
}