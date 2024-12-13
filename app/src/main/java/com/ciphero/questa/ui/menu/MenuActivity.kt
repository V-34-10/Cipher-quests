package com.ciphero.questa.ui.menu

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivityMenuBinding
import com.ciphero.questa.ui.games.SceneBasicActivity
import com.ciphero.questa.ui.privacy.PrivacyActivity
import com.ciphero.questa.ui.settings.MusicSoundPlayer
import com.ciphero.questa.ui.settings.SettingsActivity
import com.ciphero.questa.utils.AnimatorManager.setAnimateClickButton
import com.ciphero.questa.utils.AnimatorManager.startAnimateClickButton
import com.ciphero.questa.utils.DecoratorNavigationUI
import com.ciphero.questa.utils.DecoratorNavigationUI.navigateToActivity
import com.ciphero.questa.utils.PreferencesManager.setNameGameMenu
import kotlin.system.exitProcess

class MenuActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMenuBinding.inflate(layoutInflater) }
    private val musicSet by lazy { MusicSoundPlayer(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)
        musicSet.apply { playSound(R.raw.music_menu, true) }
        setupClickListenersMenu()
    }

    private fun setupClickListenersMenu() = with(binding) {
        textPrivacy.setOnClickListener {
            setAnimateClickButton(
                it,
                { navigateToActivity(PrivacyActivity::class.java, this@MenuActivity) },
                this@MenuActivity
            )
        }
        btnSettings.setOnClickListener {
            setAnimateClickButton(
                it,
                { navigateToActivity(SettingsActivity::class.java, this@MenuActivity) },
                this@MenuActivity
            )
        }
        btnFirstGame.setOnClickListener { startGame(R.string.first_game_btn, it) }
        btnSecondGame.setOnClickListener { startGame(R.string.second_game_btn, it) }
        btnThreeGame.setOnClickListener { startGame(R.string.three_game_btn, it) }
    }

    private fun startGame(gameNameResId: Int, view: View) {
        startAnimateClickButton(view, this)
        setNameGameMenu(this, gameNameResId)
        navigateToActivity(SceneBasicActivity::class.java, this@MenuActivity)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity"))
    override fun onBackPressed() {
        super.onBackPressed()
        musicSet.release()
        finishApp()
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

    private fun finishApp() {
        exitProcess(0)
    }
}