package com.ciphero.questa.ui.settings

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivitySettingsBinding
import com.ciphero.questa.utils.DecoratorNavigationUI

class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private val preferences by lazy { getSharedPreferences("CipherQuestsPref", MODE_PRIVATE) }
    private val scaleAnimation by lazy { AnimationUtils.loadAnimation(this, R.anim.anim_scale) }
    private val managerAudioService by lazy { getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    private var defaultVolumeMusic: Int = 50
    private lateinit var musicSet: MusicControllerPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)

        musicSet = MusicControllerPlayer(this)
        RunPlayer.soundMode(this, R.raw.music_menu, musicSet)

        binding.buttonResetScore.setOnClickListener {
            it.startAnimation(scaleAnimation)
            Toast.makeText(applicationContext, R.string.reset_message, Toast.LENGTH_SHORT)
                .show()
            preferences.edit().putString("balanceScores", this.getString(R.string.default_balance))
                .apply()
        }

        setVolumeControl()
    }

    private fun setVolumeControl() {
        val maxVolume = managerAudioService.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        binding.seekBarSounds.max = maxVolume
        binding.seekBarSounds.progress =
            managerAudioService.getStreamVolume(AudioManager.STREAM_MUSIC)

        binding.seekBarSounds.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    managerAudioService.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        binding.seekBarMusic.max = maxVolume
        binding.seekBarMusic.progress =
            managerAudioService.getStreamVolume(AudioManager.STREAM_MUSIC)

        binding.seekBarMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    managerAudioService.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
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

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
        musicSet.release()
    }
}