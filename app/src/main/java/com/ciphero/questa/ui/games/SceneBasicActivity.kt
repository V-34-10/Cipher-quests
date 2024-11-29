package com.ciphero.questa.ui.games

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.ciphero.questa.R
import com.ciphero.questa.databinding.ActivitySceneBasicBinding
import com.ciphero.questa.ui.games.findpair.FindPairGameFragment
import com.ciphero.questa.ui.games.puzzle.PuzzleGameFragment
import com.ciphero.questa.ui.games.quiz.QuizGameFragment
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.utils.DecoratorNavigationUI

class SceneBasicActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySceneBasicBinding.inflate(layoutInflater) }
    private val preferences by lazy { getSharedPreferences("CipherQuestsPref", MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)
        setFragmentGames()
    }

    private fun setFragmentGames() = replaceFragmentGame(
        createFragmentForGame(
            preferences.getString(
                "nameGame",
                getString(R.string.first_game_btn)
            )
        ), R.id.container_games
    )

    private fun createFragmentForGame(theme: String?): Fragment {
        return when (theme) {
            getString(R.string.second_game_btn) -> PuzzleGameFragment()
            getString(R.string.three_game_btn) -> FindPairGameFragment()
            else -> QuizGameFragment()
        }
    }

    private fun replaceFragmentGame(fragment: Fragment, containerId: Int) {
        supportFragmentManager.commit {
            replace(containerId, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (!checkBackStack()) startToMenu()
    }

    private fun checkBackStack(): Boolean {
        return if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            true
        } else false
    }

    private fun startToMenu() {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}