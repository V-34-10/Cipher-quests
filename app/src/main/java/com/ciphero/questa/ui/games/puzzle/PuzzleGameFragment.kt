package com.ciphero.questa.ui.games.puzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ciphero.questa.R
import com.ciphero.questa.adapters.puzzle.PuzzleMoveListener
import com.ciphero.questa.databinding.FragmentPuzzleGameBinding
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogPauseGamePuzzle
import com.ciphero.questa.ui.games.puzzle.controller.GovernGamePuzzle
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.ui.settings.MusicSoundPlayer
import com.ciphero.questa.utils.AnimatorManager.startAnimateClickButton
import com.ciphero.questa.utils.DecoratorNavigationUI.navigateToActivity

class PuzzleGameFragment : Fragment(), PuzzleMoveListener {
    private var _binding: FragmentPuzzleGameBinding? = null
    private val binding get() = _binding!!
    private val timer by lazy { TimeBarAnimator(null, binding) }
    private val gameGovern by lazy { GovernGamePuzzle }
    private val musicSet by lazy { MusicSoundPlayer(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPuzzleGameBinding.inflate(inflater, container, false)
        gameGovern.initGovernGamePuzzle(binding, requireContext(), this, timer)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicSet.apply { playSound(R.raw.music_puzzle, true) }
        gameGovern.startRound()

        binding.btnPause.setOnClickListener {
            startAnimateClickButton(it, requireContext())
            timer.stopTimer(binding)
            startDialogPauseGamePuzzle(this)
        }
        binding.btnBack.setOnClickListener {
            startAnimateClickButton(it, requireContext())
            navigateToActivity(MenuActivity::class.java, requireActivity())
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
    }

    override fun onMovePuzzle(move: Int) {
        gameGovern.moveCount = move
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.stopTimer(binding)
        _binding = null
    }
}