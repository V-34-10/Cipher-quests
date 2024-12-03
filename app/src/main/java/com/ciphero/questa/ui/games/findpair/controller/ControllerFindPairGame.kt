package com.ciphero.questa.ui.games.findpair.controller

import android.content.Context
import com.ciphero.questa.databinding.FragmentFindPairGameBinding
import com.ciphero.questa.ui.games.findpair.InitializationAdapter
import com.ciphero.questa.ui.games.findpair.ItemManager
import com.ciphero.questa.ui.games.findpair.ObserveClickPair
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator

class ControllerFindPairGame(
    private val context: Context,
    private val binding: FragmentFindPairGameBinding
) {

    private val gameComponents: GameFindPairComponents by lazy {
        createGameComponents()
    }

    init {
        initializeGame()
    }

    private fun createGameComponents(): GameFindPairComponents = GameFindPairComponents(
        TimeBarAnimator(this),
        InitializationAdapter(binding),
        ItemManager(),
        ObserveClickPair(
            InitializationAdapter(binding),
            ItemManager(),
            TimeBarAnimator(this)
        )
    )

    private fun initializeGame() {
        with(gameComponents) {
            pairItemManager.setupPairItems()
            initializationAdapter.apply {
                initRecyclerView(context)
                setupAdapter(pairItemManager.getPairList())
            }
            observeClickHandler.setupClickListener(binding, this@ControllerFindPairGame)
        }
    }

    fun stopGame() {
        resetGameState()
        restartGameComponents()
    }

    private fun resetGameState() {
        with(gameComponents.observeClickHandler) {
            stepSearchPair = 0
            isGameStarted = false
        }
    }

    private fun restartGameComponents() {
        with(gameComponents) {
            pairItemManager.apply {
                resetPairs()
                setupPairItems()
            }
            initializationAdapter.resetAdapter()
        }
    }
}