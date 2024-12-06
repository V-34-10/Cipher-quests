package com.ciphero.questa.ui.games.findpair.observer

import android.view.View
import android.view.animation.AnimationUtils
import com.ciphero.questa.R
import com.ciphero.questa.adapters.findpairs.InitializationAdapter
import com.ciphero.questa.databinding.FragmentFindPairGameBinding
import com.ciphero.questa.model.PairData
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogVictoryGameFindPair
import com.ciphero.questa.ui.games.findpair.FindPairGameFragment
import com.ciphero.questa.ui.games.findpair.controller.ControllerFindPairGame
import com.ciphero.questa.ui.games.findpair.image.ItemManager
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ObserveClickPair(
    private val adapterManager: InitializationAdapter,
    private val cardItemManager: ItemManager,
    private val timerAnimation: TimeBarAnimator,
    private val fragment: FindPairGameFragment,
    private val binding: FragmentFindPairGameBinding,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {

    companion object {
        private const val FLIP_DELAY_MS = 1000L
        private const val MIN_MATCHES_TO_WIN = 4
    }

    private val scaleAnimation by lazy {
        AnimationUtils.loadAnimation(
            fragment.requireContext(),
            R.anim.anim_scale
        )
    }
    private var firstSelectedPair: PairData? = null
    private var secondSelectedPair: PairData? = null
    private var isComparing = false
    var stepSearchPair = 0
    var isGameStarted = false

    fun observeClickListener(gameManager: ControllerFindPairGame) {
        adapterManager.setItemClickListener { pair, position ->
            handleClick(PairData(pair, position), gameManager)
        }
    }

    private fun handleClick(pairData: PairData, gameManager: ControllerFindPairGame) {
        if (isComparing || pairData.pair.flip || pairData.pair.match) return
        if (!isGameStarted) observeGameTimer()

        setFlipCard(pairData)

        if (firstSelectedPair == null) {
            firstSelectedPair = pairData
        } else {
            secondSelectedPair = pairData
            isComparing = true
            comparePairs(gameManager)
        }
    }

    private fun setFlipCard(pairData: PairData) {
        pairData.pair.flip()
        adapterManager.notifyItemChanged(pairData.position)
    }

    private fun comparePairs(gameManager: ControllerFindPairGame) {
        isComparing = true

        coroutineScope.launch {
            delay(FLIP_DELAY_MS)

            val isMatch = firstSelectedPair?.pair?.img == secondSelectedPair?.pair?.img

            if (isMatch) {
                firstSelectedPair?.pair?.match()
                secondSelectedPair?.pair?.match()
            } else {
                firstSelectedPair?.pair?.flip()
                secondSelectedPair?.pair?.flip()
            }

            observeGameScene(gameManager)

            firstSelectedPair = null
            secondSelectedPair = null
            isComparing = false
        }
    }

    private fun observeGameTimer() {
        timerAnimation.startTimer(fragment)
        isGameStarted = true
    }

    private fun observeGameScene(gameManager: ControllerFindPairGame) {
        stepSearchPair++
        adapterManager.notifyItemChanged(firstSelectedPair?.position ?: -1)
        adapterManager.notifyItemChanged(secondSelectedPair?.position ?: -1)
        if (isGameOver()) {
            showVictoryDialog(gameManager)
        }
    }

    private fun showVictoryDialog(gameManager: ControllerFindPairGame) {
        binding.btnOkay.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                it.startAnimation(scaleAnimation)
                startDialogVictoryGameFindPair(fragment, gameManager)
                gameManager.stopGame()
            }
        }
    }

    private fun isGameOver(): Boolean = cardItemManager.getPairList().count { it.match } / 2 >= MIN_MATCHES_TO_WIN
}