package com.ciphero.questa.ui.privacy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivityPrivacyBinding
import com.ciphero.questa.ui.daily.DailyRewardActivity
import com.ciphero.questa.utils.AnimatorManager.setAnimateClickButton
import com.ciphero.questa.utils.DecoratorNavigationUI
import com.ciphero.questa.utils.DecoratorNavigationUI.navigateToActivity
import com.ciphero.questa.utils.DecoratorNavigationUI.navigateToLink
import com.ciphero.questa.utils.PreferencesManager.setPrivacyAccepted

class PrivacyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPrivacyBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)

        binding.buttonAcceptPrivacy.setOnClickListener {
            setAnimateClickButton(it, {
                setPrivacyAccepted(this)
                navigateToActivity(DailyRewardActivity::class.java, this)
            }, this)
        }
        binding.textRead.setOnClickListener {
            setAnimateClickButton(it, { navigateToLink(this, getString(R.string.link_privacy)) }, this)
        }
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}