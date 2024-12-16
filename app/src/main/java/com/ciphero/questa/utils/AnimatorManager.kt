package com.ciphero.questa.utils

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivityMainBinding
import com.ciphero.questa.databinding.FragmentPuzzleGameBinding

object AnimatorManager {

    fun setAnimateLoadingInSplash(
        duration: Long,
        context: Context,
        binding: ActivityMainBinding
    ) = startLoadProgressBar(duration, context, binding)

    private fun startLoadProgressBar(
        duration: Long,
        context: Context,
        binding: ActivityMainBinding
    ) =
        ValueAnimator.ofInt(10, (350 * context.resources.displayMetrics.density).toInt() - 30)
            .apply {
                this.duration = duration
                addUpdateListener {
                    binding.barLoad.LineBar.layoutParams.width = it.animatedValue as Int
                    binding.barLoad.LineBar.layoutParams = binding.barLoad.LineBar.layoutParams
                }
            }.start()


    fun setAnimateClickButton(
        view: View,
        action: () -> Unit,
        context: Context
    ) = startAnimateClickButton(view, action, context)

    private fun startAnimateClickButton(view: View, action: () -> Unit, context: Context) {
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_scale))
        action()
    }

    fun startAnimateClickButton(view: View, context: Context) {
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_scale))
    }

    fun resetLineTimer(binding: FragmentPuzzleGameBinding) {
        val defaultWidth = (150 * binding.root.context.resources.displayMetrics.density).toInt()
        val layoutParams = binding.timerProgressBar.lineTimer.layoutParams
        layoutParams.width = defaultWidth
        binding.timerProgressBar.lineTimer.layoutParams = layoutParams
    }

    fun animatedPuzzle(view: View) {
        view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100).withEndAction {
            view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
        }.start()
    }
}