package com.ciphero.questa.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivitySettingsBinding
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.utils.AnimatorManager.startAnimateClickButton
import com.ciphero.questa.utils.DecoratorNavigationUI
import com.ciphero.questa.utils.DecoratorNavigationUI.navigateToActivity
import com.ciphero.questa.utils.PreferencesManager.resetScoresSettings

class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private val musicSet by lazy { MusicSoundPlayer(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)
        musicSet.apply { playSound(R.raw.music_menu, true) }

        binding.buttonResetScore.setOnClickListener {
            startAnimateClickButton(it, this)
            Toast.makeText(applicationContext, R.string.reset_message, Toast.LENGTH_SHORT).show()
            resetScoresSettings(this)
        }
        musicSet.observeVolumeControl(binding, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        musicSet.release()
    }

    override fun onResume() {
        super.onResume()
        musicSet.resume()
    }

    override fun onPause() {
        super.onPause()
        musicSet.pause()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        navigateToActivity(MenuActivity::class.java, this)
    }
}