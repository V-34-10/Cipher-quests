package com.ciphero.questa.ui.games.puzzle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.ciphero.questa.R
import com.ciphero.questa.adapters.puzzle.PuzzleMoveListener
import com.ciphero.questa.databinding.FragmentPuzzleGameBinding
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogPauseGamePuzzle
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator
import com.ciphero.questa.ui.menu.MenuActivity

class PuzzleGameFragment : Fragment(), PuzzleMoveListener {
    private var _binding: FragmentPuzzleGameBinding? = null
    private val binding get() = _binding!!
    private val scaleAnimation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.anim_scale
        )
    }
    private lateinit var timer: TimeBarAnimator
    private lateinit var gameGovern: GovernGamePuzzle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPuzzleGameBinding.inflate(inflater, container, false)
        gameGovern = GovernGamePuzzle
        gameGovern.initGovernGamePuzzle(binding, requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = TimeBarAnimator( null)
        gameGovern.startRound()

        binding.btnPause.setOnClickListener {
            it.startAnimation(scaleAnimation)
            startDialogPauseGamePuzzle(this)
        }

        binding.btnBack.setOnClickListener {
            it.startAnimation(scaleAnimation)
            startActivity(Intent(context, MenuActivity::class.java))
            activity?.finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.stopTimer(binding)
    }

    override fun onMovePuzzle(move: Int) {
        gameGovern.moveCount = move
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}