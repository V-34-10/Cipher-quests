package com.ciphero.questa.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivityMenuBinding
import com.ciphero.questa.ui.games.SceneBasicActivity
import com.ciphero.questa.ui.privacy.PrivacyActivity
import com.ciphero.questa.ui.settings.SettingsActivity
import com.ciphero.questa.utils.DecoratorNavigationUI
import kotlin.system.exitProcess

class MenuActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMenuBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)
        controlButtons()
    }

    private fun controlButtons() {
        val animator = AnimationUtils.loadAnimation(this@MenuActivity, R.anim.anim_scale)
        binding.textPrivacy.setOnClickListener {
            it.startAnimation(animator)
            startActivity(Intent(this@MenuActivity, PrivacyActivity::class.java))
            finish()
        }
        binding.apply {
            clickButton(btnSettings, SettingsActivity::class.java)
            clickGameButton(btnFirstGame, R.string.first_game_btn)
            clickGameButton(btnSecondGame, R.string.second_game_btn)
            clickGameButton(btnThreeGame, R.string.three_game_btn)
        }
    }

    private fun clickGameButton(button: View, gameName: Int) {
        val animator = AnimationUtils.loadAnimation(this@MenuActivity, R.anim.anim_scale)
        button.setOnClickListener {
            it.startAnimation(animator)
            this.getSharedPreferences("CipherQuestsPref", MODE_PRIVATE).edit()
                .putString("nameGame", this.getString(gameName)).apply()
            startActivity(Intent(this@MenuActivity, SceneBasicActivity::class.java))
            finish()
        }
    }

    private fun <T : AppCompatActivity> clickButton(button: View, activityClass: Class<T>) {
        val animator = AnimationUtils.loadAnimation(this@MenuActivity, R.anim.anim_scale)
        button.setOnClickListener {
            it.startAnimation(animator)
            startActivity(Intent(this@MenuActivity, activityClass))
            finish()
        }
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