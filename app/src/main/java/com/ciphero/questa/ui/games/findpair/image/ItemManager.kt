package com.ciphero.questa.ui.games.findpair.image

import com.ciphero.questa.model.FindPair

class ItemManager(private val numPairs: Int) {

    private val pairList = mutableListOf<FindPair>()

    private fun generatePairs(images: List<Int> = GeneratorImageFindPair.getImages()): List<FindPair> {
        val pairs = images.take(numPairs).flatMap { image ->
            listOf(
                FindPair(image, id = pairList.size),
                FindPair(image, id = pairList.size + 1)
            )
        }.shuffled()

        return if (images.size > numPairs) {
            val uniqueImage = images[numPairs] // Get the next available image
            (pairs + FindPair(uniqueImage, id = pairList.size)).shuffled()
        } else {
            pairs
        }
    }

    fun setupPairItems(images: List<Int> = GeneratorImageFindPair.getImages()) {
        pairList.clear()
        pairList.addAll(generatePairs(images))
    }

    fun getPairList(): List<FindPair> = pairList

    fun resetPairs() = pairList.forEach { it.reset() }
}