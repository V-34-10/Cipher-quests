package com.ciphero.questa.ui.games.findpair

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.ciphero.questa.R
import com.ciphero.questa.databinding.FragmentFindPairGameBinding
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogPauseGameFindPair
import com.ciphero.questa.ui.games.findpair.controller.ControllerFindPairGame
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.ui.settings.MusicControllerPlayer

class FindPairGameFragment : Fragment() {
    private var _binding: FragmentFindPairGameBinding? = null
    private val binding get() = _binding!!
    private val scaleAnimation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.anim_scale
        )
    }
    private lateinit var gameGovern: ControllerFindPairGame
    private lateinit var musicSet: MusicControllerPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindPairGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicSet = MusicControllerPlayer(requireContext())
        musicSet.apply { playSound(R.raw.music_find_pair, true) }

        gameGovern = ControllerFindPairGame(requireContext(), binding)

        binding.btnPause.setOnClickListener {
            it.startAnimation(scaleAnimation)
            startDialogPauseGameFindPair(this, gameGovern)
        }

        binding.btnBack.setOnClickListener {
            it.startAnimation(scaleAnimation)
            gameGovern.stopGame()
            startActivity(Intent(requireContext(), MenuActivity::class.java))
            activity?.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        musicSet.resume()
    }

    override fun onPause() {
        super.onPause()
        musicSet.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        musicSet.release()
        gameGovern.stopGame()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}