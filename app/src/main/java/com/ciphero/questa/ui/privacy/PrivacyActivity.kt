package com.ciphero.questa.ui.privacy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivityPrivacyBinding
import com.ciphero.questa.ui.daily.DailyRewardActivity
import com.ciphero.questa.utils.DecoratorNavigationUI

class PrivacyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPrivacyBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)
        agreePrivacy()
        runIntentPrivacyLink()
    }

    private fun setStatusAcceptPrivacy() =
        this.getSharedPreferences("CipherQuestsPref", MODE_PRIVATE).edit()
            .putBoolean("Privacy", true).apply()

    private fun agreePrivacy() {
        with(binding) {
            buttonAcceptPrivacy.setOnClickListener { view ->
                performClickAction(view) {
                    setStatusAcceptPrivacy()
                    launchActivity(DailyRewardActivity::class.java)
                }
            }
        }
    }

    private fun runIntentPrivacyLink() {
        with(binding) {
            textRead.setOnClickListener { view ->
                performClickAction(view) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.link_privacy))
                        )
                    )
                }
            }
        }
    }

    private fun performClickAction(view: View, action: () -> Unit) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_scale))
        action()
    }

    private fun <T> launchActivity(activityClass: Class<T>) {
        startActivity(Intent(this, activityClass))
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}