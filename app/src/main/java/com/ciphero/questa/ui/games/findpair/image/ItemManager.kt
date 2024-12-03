package com.ciphero.questa.ui.games.findpair.image

import com.ciphero.questa.model.FindPair

class ItemManager(private val numPairs: Int = 4) {
    /*private val numPairs: Int = 4
    private val pairList = mutableListOf<FindPair>()
    private val imageList = mutableListOf<Int>()*/

    /*fun setupPairItems() {
        pairList.clear()
        imageList.apply {
            clear()
            addAll(GeneratorImageFindPair.generateImageGrid())
        }

        var position = 0
        repeat(numPairs) {
            pairList.add(FindPair(imageList[it], id = position++))
            pairList.add(FindPair(imageList[it], id = position++))
        }

        pairList.shuffle()
    }

    fun getPairList(): List<FindPair> = pairList

    fun resetPairs() = pairList.forEach { it.reset() }

    private fun FindPair.reset() {
        flip = false
        match = false
    }*/

    val imageList by lazy {
        GeneratorImageFindPair.generateImageGrid().take(numPairs).flatMap { listOf(it, it) }
            .shuffled()
    }

    fun getPairList(): List<FindPair> =
        imageList.mapIndexed { index, img -> FindPair(img, id = index) }

    fun resetPairs() {
        getPairList().forEach { it.flip = false; it.match = false }
    }
}