package com.example.math_for_kids_application.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.math_for_kids_application.R
import com.example.math_for_kids_application.databinding.FragmentGameBinding
import com.example.math_for_kids_application.domain.entities.GameResult
import com.example.math_for_kids_application.domain.entities.Level
import com.example.math_for_kids_application.presentation.viewmodel.GameFragmentViewModel
import com.example.math_for_kids_application.presentation.viewmodel.GameViewModelFactory

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val viewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val viewModel: GameFragmentViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[GameFragmentViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenersToOptions()
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNum.toString()
            for (i in 0 until tvOptions.size) {
                if (i < it.options.size) {
                    tvOptions[i].text = it.options[i].toString()
                }
            }
        }

        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }

        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {

        }

        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val backgroundResId = if (it) {
                R.drawable.green_progress_background
            } else {
                R.drawable.custom_progress_background
            }
            val background = ContextCompat.getDrawable(requireContext(), backgroundResId)
            binding.progressBar.progressDrawable = background
        }

        viewModel.formattedPreGameTime.observe(viewLifecycleOwner) {
            binding.tvPregameTimer.visibility = View.VISIBLE
            binding.tvPregameTimer.text = it
        }
        viewModel.isGameStarted.observe(viewLifecycleOwner) {
            binding.viewsGroup.visibility = View.VISIBLE
            binding.tvPregameTimer.visibility = View.GONE
        }

        viewModel.formattedGameTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }

        /*viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }*/

        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishFragment(it)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
    }

    private fun setClickListenersToOptions() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                viewModel.chooseAnswers(tvOption.text.toString().toInt())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFinishFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragment2ToGameFinishedFragment2(
                gameResult
            )
        )
    }
}