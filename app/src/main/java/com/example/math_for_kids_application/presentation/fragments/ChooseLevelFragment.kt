package com.example.math_for_kids_application.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.math_for_kids_application.databinding.FragmentChooseLevelBinding
import com.example.math_for_kids_application.domain.entities.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {

        with(binding){
            btnTestLevel.setOnClickListener{
                launchGameFragment(Level.TEST)
            }
            btnEasyLevel.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            btnNormalLevel.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            btnHardLevel.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }
    }

    private fun launchGameFragment(level:Level) {
        findNavController().navigate(ChooseLevelFragmentDirections.actionChooseLevelFragment3ToGameFragment2(level))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}