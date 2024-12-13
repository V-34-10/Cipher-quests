package com.ciphero.questa.ui.settings

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.SeekBar
import androidx.annotation.RawRes
import com.ciphero.questa.databinding.ActivitySettingsBinding
import timber.log.Timber
import java.io.IOException

class MusicSoundPlayer(private val context: Context) {

    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }
    private var currentSoundResId: Int? = null
    private var isPlaying = false


    fun playSound(@RawRes soundResId: Int, loop: Boolean = false) {
        if (currentSoundResId == soundResId && isPlaying) return

        resetMediaPlayer()

        try {
            context.resources.openRawResourceFd(soundResId)?.use { afd ->
                mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                mediaPlayer.isLooping = loop
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener {
                    it.start()
                    isPlaying = true
                }

                currentSoundResId = soundResId
            } ?: throw RuntimeException("Error opening raw resource")

        } catch (e: IOException) {
            Timber.e(e, "Error playing sound: $soundResId")
        }
    }

    private fun resetMediaPlayer() {
        mediaPlayer.apply {
            if (isPlaying) {
                stop()
                this@MusicSoundPlayer.isPlaying = false
            }
            reset()
            setOnPreparedListener(null)
        }
        currentSoundResId = null
    }


    fun pause() {
        if (isPlaying) {
            mediaPlayer.pause()
            isPlaying = false
        }
    }


    fun resume() {
        if (!isPlaying && currentSoundResId != null) {
            mediaPlayer.start()
            isPlaying = true
        }
    }


    fun release() {
        resetMediaPlayer()
        mediaPlayer.release()
    }

    fun observeVolumeControl(binding: ActivitySettingsBinding, context: Context) {
        val managerAudioService = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        binding.seekBarSounds.max =
            managerAudioService.getStreamMaxVolume(AudioManager.STREAM_SYSTEM)
        binding.seekBarSounds.progress =
            managerAudioService.getStreamVolume(AudioManager.STREAM_SYSTEM)

        binding.seekBarSounds.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) managerAudioService.setStreamVolume(
                    AudioManager.STREAM_SYSTEM,
                    progress,
                    0
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        binding.seekBarMusic.max = managerAudioService.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        binding.seekBarMusic.progress =
            managerAudioService.getStreamVolume(AudioManager.STREAM_MUSIC)

        binding.seekBarMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) managerAudioService.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    progress,
                    0
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}