package com.ciphero.questa.ui.games.findpair.controller

import com.ciphero.questa.ui.games.findpair.InitializationAdapter
import com.ciphero.questa.ui.games.findpair.ItemManager
import com.ciphero.questa.ui.games.findpair.ObserveClickPair
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator

data class GameFindPairComponents(
    val timer: TimeBarAnimator,
    val initializationAdapter: InitializationAdapter,
    val pairItemManager: ItemManager,
    val observeClickHandler: ObserveClickPair
)