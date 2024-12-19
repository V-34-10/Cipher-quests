package com.ciphero.questa.ui.games.quiz

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciphero.questa.R
import com.ciphero.questa.databinding.FragmentQuizGameBinding
import com.ciphero.questa.model.CasinoQuizQuestions
import com.ciphero.questa.model.Question
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame
import com.ciphero.questa.ui.settings.MusicSoundPlayer
import com.ciphero.questa.utils.BindingManager.controlButtonGames

class QuizGameFragment : Fragment() {
    private var _binding: FragmentQuizGameBinding? = null
    private val binding get() = _binding!!
    private var currentQuestion: Question? = null
    private val musicSet by lazy { MusicSoundPlayer(requireContext()) }
    private val answerButtons by lazy {
        arrayOf(
            binding.btnFirstAnswer,
            binding.btnSecondAnswer,
            binding.btnThreeAnswer
        )
    }
    private var questionCount = 0
    private var correctAnswers = 0
    private var incorrectAnswers = 0
    private val totalQuestions = 10
    private val askedQuestions = mutableSetOf<Question>()
    private val availableQuestions = CasinoQuizQuestions.questions.toMutableList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicSet.apply { playSound(R.raw.music_quiz, true) }
        showNextQuestion()

        val answerClickListener = View.OnClickListener {
            checkAnswer(it as TextView)
            disableAnswerButtons()
        }

        answerButtons.forEach { it.setOnClickListener(answerClickListener) }
        controlButtonGames(
            binding,
            requireContext(),
            this,
            requireActivity(),
            resetAnswerButtonBackgrounds = { resetAnswerButtonBackgrounds() },
            showNextQuestion = { showNextQuestion() })
    }

    private fun resetAnswerButtonBackgrounds() =
        answerButtons.forEach { it.setBackgroundResource(R.drawable.background_basic_answer) }

    private fun disableAnswerButtons() = answerButtons.forEach { it.isEnabled = false }
    private fun enableAnswerButtons() = answerButtons.forEach { it.isEnabled = true }

    private fun showNextQuestion() {
        if (questionCount < totalQuestions) {
            questionCount++

            currentQuestion = availableQuestions.random().also {
                availableQuestions.remove(it)
                askedQuestions.add(it)
            }
            binding.textQuestion.text = currentQuestion?.text

            val answers = currentQuestion?.answers?.shuffled() ?: emptyList()
            answerButtons.forEachIndexed { index, button ->
                button.text = answers.getOrNull(index)
                button.isEnabled = true
            }

            updateStatusText()
        } else {
            DialogsBaseGame.startDialogLoseGameQuiz(this) { resetGame() }
        }
    }

    private fun updateStatusText() {
        binding.textStatus.text = getString(R.string.question_status, questionCount, totalQuestions)
    }

    private fun checkAnswer(button: TextView) {
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

            }
        })

        if (isCorrect) {
            correctAnswers++
        } else {
            incorrectAnswers++
            if (incorrectAnswers >= 3) {
                DialogsBaseGame.startDialogLoseGameQuiz(this) { resetGame() }
            }
        }

        if (questionCount == totalQuestions && incorrectAnswers < 3) {
            DialogsBaseGame.startDialogVictoryGameQuiz(this) { resetGame() }
        } else {
            binding.btnNext.visibility = View.VISIBLE
            binding.btnNext.isEnabled = true
        }
    }

    private fun resetGame() {
        questionCount = 0
        correctAnswers = 0
        incorrectAnswers = 0
        binding.btnNext.isEnabled = false
        binding.btnNext.visibility = View.GONE
        showNextQuestion()
        resetAnswerButtonBackgrounds()
        enableAnswerButtons()
        askedQuestions.clear()
        availableQuestions.addAll(CasinoQuizQuestions.questions)
        availableQuestions.shuffle()
        musicSet.resume()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}