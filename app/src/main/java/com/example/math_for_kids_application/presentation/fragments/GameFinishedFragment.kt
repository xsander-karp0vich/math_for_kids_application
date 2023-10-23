package com.example.math_for_kids_application.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.example.math_for_kids_application.R
import com.example.math_for_kids_application.databinding.FragmentGameFinishedBinding
import com.example.math_for_kids_application.domain.entities.GameResult
import kotlin.random.Random


class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupGameFinishedScreen()
    }

    private fun setupGameFinishedScreen() {

        val randomNumber = Random.nextInt(1,4)

        if (gameResult.winner){
            binding.tvMessageOfGameResults.text = getString(R.string.you_won)
            binding.buttonRetry.text = getString(R.string.go_back)

            when(randomNumber){
                1 -> binding.emojiResult.setImageResource(R.drawable.win_pic1)
                2 -> binding.emojiResult.setImageResource(R.drawable.win_pic2)
                3 -> binding.emojiResult.setImageResource(R.drawable.win_pic3)
                4 -> binding.emojiResult.setImageResource(R.drawable.win_pic1)
            }

        } else {
            binding.tvMessageOfGameResults.text = getString(R.string.you_lose)
            binding.buttonRetry.text = getString(R.string.try_again)

            when(randomNumber){
                1 -> binding.emojiResult.setImageResource(R.drawable.lose_pic1)
                2 -> binding.emojiResult.setImageResource(R.drawable.lose_pic2)
                3 -> binding.emojiResult.setImageResource(R.drawable.lose_pic3)
                4 -> binding.emojiResult.setImageResource(R.drawable.lose_pic4)
            }
        }

        binding.tvScore.text = String.format(
            getString(R.string.your_score),
            gameResult.countOfRightAnswers
        )
        binding.tvScorePercents.text = String.format(
            getString(R.string.your_score_in),
            calculatePercentOfRightAnswers()
        )

    }
    private fun calculatePercentOfRightAnswers(): Int{
        return ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble()) * 100).toInt()
    }


    private fun setupClickListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME,FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT,gameResult)
                }
            }
        }
    }
}