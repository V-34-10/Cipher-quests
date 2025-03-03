package com.ciphero.questa.ui.games.findpair

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ciphero.questa.R
import com.ciphero.questa.databinding.FragmentFindPairGameBinding
import com.ciphero.questa.ui.games.findpair.controller.ControllerFindPairGame
import com.ciphero.questa.ui.settings.MusicSoundPlayer
import com.ciphero.questa.utils.BindingManager.controlButtonGames

class FindPairGameFragment : Fragment() {
    private var _binding: FragmentFindPairGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameGovern: ControllerFindPairGame
    private val musicSet by lazy { MusicSoundPlayer(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindPairGameBinding.inflate(inflater, container, false)
        gameGovern = ControllerFindPairGame(binding, this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicSet.apply { playSound(R.raw.music_find_pair, true) }
        controlButtonGames(
            binding,
            requireContext(),
            this,
            requireActivity(),
            gameGovern = gameGovern
        )
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        gameGovern.stopGame()
        _binding = null
    }
}