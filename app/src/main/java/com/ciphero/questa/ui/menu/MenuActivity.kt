package com.ciphero.questa.ui.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivityMenuBinding
import com.ciphero.questa.ui.games.SceneBasicActivity
import com.ciphero.questa.ui.privacy.PrivacyActivity
import com.ciphero.questa.ui.settings.MusicControllerPlayer
import com.ciphero.questa.ui.settings.SettingsActivity
import com.ciphero.questa.utils.DecoratorNavigationUI
import kotlin.system.exitProcess

class MenuActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMenuBinding.inflate(layoutInflater) }
    private val preferences by lazy { getSharedPreferences("CipherQuestsPref", MODE_PRIVATE) }
    private val scaleAnimation by lazy { AnimationUtils.loadAnimation(this, R.anim.anim_scale) }
    private lateinit var musicSet: MusicControllerPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)
        musicSet = MusicControllerPlayer(this)
        musicSet.apply { playSound(R.raw.music_menu, true) }
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding) {
        textPrivacy.setOnClickListener { navigateToActivity<PrivacyActivity>(it) }
        btnSettings.setOnClickListener { navigateToActivity<SettingsActivity>(it) }
        btnFirstGame.setOnClickListener { startGame(R.string.first_game_btn, it) }
        btnSecondGame.setOnClickListener { startGame(R.string.second_game_btn, it) }
        btnThreeGame.setOnClickListener { startGame(R.string.three_game_btn, it) }
    }

    private fun <T : Activity> navigateToActivity(view: View, activityClass: Class<T>) {
        view.startAnimation(scaleAnimation)
        startActivity(Intent(this, activityClass))
        finish()
    }

    private inline fun <reified T : Activity> navigateToActivity(view: View) {
        navigateToActivity(view, T::class.java)
    }

    private fun startGame(gameNameResId: Int, view: View) {
        view.startAnimation(scaleAnimation)
        preferences.edit { putString("nameGame", getString(gameNameResId)) }
        startActivity(Intent(this, SceneBasicActivity::class.java))
        finish()
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
        finishApp()
    }

    private fun finishApp() {
        exitProcess(0)
    }
}