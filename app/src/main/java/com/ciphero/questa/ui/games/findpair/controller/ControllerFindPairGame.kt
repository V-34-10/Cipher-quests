package com.ciphero.questa.ui.games.findpair.controller

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.ciphero.questa.databinding.FragmentFindPairGameBinding
import com.ciphero.questa.adapters.findpairs.InitializationAdapter
import com.ciphero.questa.ui.games.findpair.image.ItemManager
import com.ciphero.questa.ui.games.findpair.observer.ObserveClickPair
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
        InitializationAdapter(),
        ItemManager(),
        ObserveClickPair(
            InitializationAdapter(),
            ItemManager(),
            TimeBarAnimator(this)
        )
    )

    private fun initializeGame() {
        with(gameComponents) {
            pairItemManager.imageList
            initializationAdapter.apply {
                binding.sceneFindGame.layoutManager = GridLayoutManager(context, 3)
                setAdapter(pairItemManager.getPairList())
            }
            observeClickHandler.setupClickListener(binding, this@ControllerFindPairGame)
        }
    }

    fun stopGame() {
        setGameStarted()
        restartGameComponents()
    }

    private fun setGameStarted() {
        gameComponents.observeClickHandler.clickHandler.isGameStarted = false
    }

    private fun restartGameComponents() {
        with(gameComponents) {
            pairItemManager.apply {
                resetPairs()
                imageList
            }
            initializationAdapter.resetAdapter()
        }
    }
}