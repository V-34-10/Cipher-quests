package com.ciphero.questa.ui.privacy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivityPrivacyBinding
import com.ciphero.questa.ui.daily.DailyRewardActivity
import com.ciphero.questa.utils.DecoratorNavigationUI

class PrivacyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPrivacyBinding.inflate(layoutInflater) }
    private val preferences by lazy { getSharedPreferences("CipherQuestsPref", MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.buttonAcceptPrivacy.setOnClickListener {
            animateClick(it) { acceptPrivacyAndNavigate() }
        }
        binding.textRead.setOnClickListener {
            animateClick(it) { openPrivacyLink() }
        }
    }

    private fun acceptPrivacyAndNavigate() {
        setPrivacyAccepted()
        navigateToActivity(DailyRewardActivity::class.java)
    }

    private fun setPrivacyAccepted() = preferences.edit { putBoolean("Privacy", true) }

    private fun openPrivacyLink() = startActivity(
        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_privacy)))
    )

    private fun animateClick(view: View, action: () -> Unit) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_scale))
        action()
    }

    private fun <T> navigateToActivity(activityClass: Class<T>) {
        startActivity(Intent(this, activityClass))
        finish()
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}