package com.ciphero.questa.ui.games.findpair.image

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.bumptech.glide.Glide
import com.ciphero.questa.R

fun List<Int>.repeat(repeatTimes: Int): List<Int> {
    val result = mutableListOf<Int>()
    repeat(repeatTimes) {
        result.addAll(this)
    }
    return result
}

fun ImageView.loadGlideImageBack(url: String?) =
    Glide.with(this.context).load(url).placeholder(getDefaultImageGame(this.id))
        .error(getDefaultImageGame(this.id)).into(this)

@DrawableRes
private fun getDefaultImageGame(@IdRes imageViewId: Int): Int = when (imageViewId) {
    R.id.btn_first_game -> R.drawable.button_quiz
    R.id.btn_second_game -> R.drawable.button_puzzles
    R.id.btn_three_game -> R.drawable.button_find_pair
    else -> R.drawable.button_quiz
}