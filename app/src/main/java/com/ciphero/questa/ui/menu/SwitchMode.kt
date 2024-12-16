package com.ciphero.questa.ui.menu

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciphero.questa.R
import com.ciphero.questa.adapters.offerwall.OfferwallAdapter
import com.ciphero.questa.databinding.ActivityMenuBinding
import com.ciphero.questa.model.Offerwall
import com.ciphero.questa.ui.games.SceneBasicActivity
import com.ciphero.questa.ui.games.findpair.image.loadGlideImageBack
import com.ciphero.questa.utils.DecoratorNavigationUI.navigateToActivity
import com.ciphero.questa.utils.DecoratorNavigationUI.navigateToLink
import com.ciphero.questa.utils.PreferencesManager.setNameGameMenu
import timber.log.Timber

class SwitchMode(
    private val context: Context,
    private val binding: ActivityMenuBinding,
    private val activity: MenuActivity
) {
    private lateinit var adapter: OfferwallAdapter

    @SuppressLint("TimberArgCount")
    fun setDemoMode(listGamesOffer: List<Offerwall>) {
        val imageButtons = listOf(
            binding.btnFirstGame,
            binding.btnSecondGame,
            binding.btnThreeGame
        )
        listGamesOffer.forEachIndexed { i, item ->
            if (i < imageButtons.size) {
                Timber.tag("MenuActivity")
                    .d("%s%s", "%s ", "Games received: gameVisualFile %s", i, item.bgUrl)
                imageButtons[i].loadGlideImageBack(item.bgUrl)
            } else {
                Timber.tag("MenuActivity")
                    .w("More games received than buttons. Skipping index %s", i)
                return
            }
        }
        showDemoModeUI()
    }

    private fun showDemoModeUI() {
        binding.controlButton.visibility = View.VISIBLE
        binding.listButtonsGames.visibility = View.VISIBLE
    }

    @SuppressLint("TimberArgCount")
    fun setWorkMode(games: List<Offerwall>) {
        setupWorkModeUI(games)

        adapter = OfferwallAdapter(this::onGameClick)
        binding.offerwallList.adapter = adapter
        adapter.updateOfferwallList(games)

        Timber.tag("MenuActivity").d("Games received:")
        for (game in games) {
            Timber.tag("MenuActivity")
                .d(
                    "%s%s",
                    "%s, play: ",
                    "%s%s",
                    "%s, fgUrl: ",
                    "%s%s",
                    "%s, bgUrl: ",
                    "%s%s",
                    "%s, title: ",
                    "inx: %s",
                    game.inx,
                    game.title,
                    game.bgUrl,
                    game.fgUrl,
                    game.play
                )
        }
        Timber.tag("MenuActivity").d("Games received: %s", games.size)
    }

    private fun setupWorkModeUI(games: List<Offerwall>) {
        binding.mainMenu.setBackgroundResource(R.drawable.background_main_blur)
        binding.titleOffers.visibility = View.VISIBLE
        binding.offerwallList.visibility = View.VISIBLE
        binding.offerwallList.layoutManager = if (games.size > 3) {
            GridLayoutManager(context, 2)
        } else {
            LinearLayoutManager(context)
        }
    }

    private fun onGameClick(games: Offerwall) {
        if (games.title.startsWith("https://")) {
            navigateToLink(activity, games.title)
        } else {
            val gameName = games.inx?.let { getGameNameByIndex(it) }
            gameName?.let { setNameGameMenu(context, it) }
            navigateToActivity(SceneBasicActivity::class.java, activity)
        }
    }

    private fun getGameNameByIndex(index: Int): String? = when (index) {
        0 -> activity.getString(R.string.first_game_btn)
        1 -> activity.getString(R.string.second_game_btn)
        2 -> activity.getString(R.string.three_game_btn)
        else -> null
    }
}