package com.ciphero.questa.ui.splash

import android.animation.ValueAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ciphero.questa.databinding.ActivityMainBinding
import com.ciphero.questa.ui.privacy.PrivacyActivity
import com.ciphero.questa.utils.DecoratorNavigationUI
import com.ciphero.questa.utils.NetworkCheck
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)
        if (NetworkCheck.checkAccessInternet(this)) {
            /*animationLoading(15000L)
            lifecycleScope.launch {
                delay(3000L)
                loadingOfferWall()
            }*/
        } else {
            animProgressBar(3000L)
            lifecycleScope.launch {
                delay(3000L)
                startActivity(Intent(this@MainActivity, PrivacyActivity::class.java))
                finish()
            }
        }
    }

    private fun animProgressBar(duration: Long) {
        val animator =
            ValueAnimator.ofInt(10, (350 * resources.displayMetrics.density).toInt() - 10).apply {
                this.duration = duration
                addUpdateListener {
                    binding.barLoad.LineBar.layoutParams.width = it.animatedValue as Int
                    binding.barLoad.LineBar.layoutParams = binding.barLoad.LineBar.layoutParams
                }
            }

        animator.start()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}