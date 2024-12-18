package com.ciphero.questa.ui.games

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
import com.ciphero.questa.utils.DecoratorNavigationUI.navigateToActivity
import com.ciphero.questa.utils.PreferencesManager.getNameGameMenu

class SceneBasicActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySceneBasicBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        DecoratorNavigationUI.hideNavigationBar(this)
        setFragmentGame(createFragmentForGame(getNameGameMenu(this)), R.id.container_games)
    }

    private fun createFragmentForGame(theme: String?): Fragment = when (theme) {
        getString(R.string.second_game_btn) -> PuzzleGameFragment()
        getString(R.string.three_game_btn) -> FindPairGameFragment()
        else -> QuizGameFragment()
    }

    private fun setFragmentGame(fragment: Fragment, containerId: Int) =
        supportFragmentManager.commit {
            replace(containerId, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (!checkBackStack()) navigateToActivity(MenuActivity::class.java, this)
    }

    private fun checkBackStack(): Boolean {
        return if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            true
        } else false
    }
}