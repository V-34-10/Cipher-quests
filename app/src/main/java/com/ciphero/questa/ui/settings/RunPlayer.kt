package com.ciphero.questa.ui.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

object RunPlayer {
    fun musicMode(context: Context, sourceMusic: Int, managerMusic: MusicControllerPlayer) {
        if (context.getSharedPreferences("CipherQuestsPref", AppCompatActivity.MODE_PRIVATE)
                .getBoolean("MusicStatus", false)
        ) managerMusic.apply { playSound(sourceMusic, true) }
    }

    fun soundMode(context: Context, sourceMusic: Int, managerMusic: MusicControllerPlayer) {
        if (context.getSharedPreferences("CipherQuestsPref", AppCompatActivity.MODE_PRIVATE)
                .getBoolean("SoundStatus", false)
        ) managerMusic.apply { playSound(sourceMusic, true) }
    }
}