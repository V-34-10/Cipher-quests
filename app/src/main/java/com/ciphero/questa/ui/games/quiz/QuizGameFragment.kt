package com.ciphero.questa.ui.games.quiz

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciphero.questa.R
import com.ciphero.questa.databinding.FragmentQuizGameBinding
import com.ciphero.questa.model.CasinoQuizQuestions
import com.ciphero.questa.model.Question
import com.ciphero.questa.ui.menu.MenuActivity
import com.ciphero.questa.ui.settings.MusicControllerPlayer

class QuizGameFragment : Fragment() {
    private var _binding: FragmentQuizGameBinding? = null
    private val binding get() = _binding!!
    private var currentQuestion: Question? = null
    private var selectedAnswerButton: TextView? = null
    private lateinit var musicSet: MusicControllerPlayer
    private val scaleAnimation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.anim_scale
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicSet = MusicControllerPlayer(requireContext())
        musicSet.apply { playSound(R.raw.music_quiz, true) }

        showNextQuestion()

        val answerClickListener = View.OnClickListener {
            checkAnswer(it as TextView)
        }

        binding.btnFirstAnswer.setOnClickListener(answerClickListener)
        binding.btnSecondAnswer.setOnClickListener(answerClickListener)
        binding.btnThreeAnswer.setOnClickListener(answerClickListener)

        binding.btnNext.setOnClickListener {
            it.startAnimation(scaleAnimation)
            selectedAnswerButton?.background = null
            selectedAnswerButton = null
            binding.btnNext.visibility = View.GONE
            showNextQuestion()
        }
        binding.btnBack.setOnClickListener {
            it.startAnimation(scaleAnimation)
            startActivity(Intent(requireContext(), MenuActivity::class.java))
            activity?.finish()
        }
    }

    private fun showNextQuestion() {
        currentQuestion = CasinoQuizQuestions.questions.random()
        binding.textQuestion.text = currentQuestion?.text

        val answers = currentQuestion?.answers?.shuffled() ?: emptyList()

        with(binding) {
            btnFirstAnswer.text = answers.getOrNull(0)
            btnSecondAnswer.text = answers.getOrNull(1)
            btnThreeAnswer.text = answers.getOrNull(2)

            btnFirstAnswer.isEnabled = true
            btnSecondAnswer.isEnabled = true
            btnThreeAnswer.isEnabled = true

            btnNext.visibility = View.GONE
        }
    }

    private fun checkAnswer(button: TextView) {
        selectedAnswerButton = button
        val isCorrect = button.text == currentQuestion?.correctAnswer

        val animationResId =
            if (isCorrect) R.animator.correct_answer_animation else R.animator.incorrect_answer_animation
        val animator = AnimatorInflater.loadAnimator(requireContext(), animationResId)
        animator.setTarget(button)
        animator.start()

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val backgroundDrawable =
                    if (isCorrect) R.drawable.background_correct_answer else R.drawable.background_nocorrect_answer
                button.setBackgroundResource(backgroundDrawable)
                binding.btnNext.visibility = View.VISIBLE
            }
        })

        with(binding) {
            btnFirstAnswer.isEnabled = false
            btnSecondAnswer.isEnabled = false
            btnThreeAnswer.isEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}