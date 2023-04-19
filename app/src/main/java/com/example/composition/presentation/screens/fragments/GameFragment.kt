package com.example.composition.presentation.screens.fragments

import android.content.res.ColorStateList
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
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.presentation.viewmodels.GameViewModel
import com.example.composition.presentation.viewmodels.GameViewModelFactory


class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val viewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }
//    private val tvOptions by lazy {
//        with(binding) {
//            mutableListOf<TextView>().apply {
//                add(tvOption1)
//                add(tvOption2)
//                add(tvOption3)
//                add(tvOption4)
//                add(tvOption5)
//                add(tvOption6)
//            }
//        }
//    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentGameBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
//        setClickListenersToOptions()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

//    private fun setClickListenersToOptions() {
//        for (tvOption in tvOptions) {
//            tvOption.setOnClickListener {
//                viewModel.chooseAnswer(tvOption.text.toString().toInt())
//            }
//        }
//    }

    private fun observeViewModel() = with(binding) {
//        viewModel.question.observe(viewLifecycleOwner) {
//            tvSum.text = it.sum.toString()
//            tvVisibleNum.text = it.visibleNumber.toString()
//            for (i in 0 until tvOptions.size) {
//                tvOptions[i].text = it.options[i].toString()
//            }
//        }
//        viewModel.percentOfRightAnswer.observe(viewLifecycleOwner) {
//            progressBar.setProgress(it, true)
//        }
//        viewModel.enoughCount.observe(viewLifecycleOwner) {
//            tvAnswerProgress.setTextColor(getColorByState(it))
//        }
//        viewModel.enoughPercent.observe(viewLifecycleOwner) {
//            val color = getColorByState(it)
//            progressBar.progressTintList = ColorStateList.valueOf(color)
//        }
//        viewModel.formattedTime.observe(viewLifecycleOwner) {
//            tvTime.text = it
//        }
//        viewModel.minPercent.observe(viewLifecycleOwner) {
//            progressBar.secondaryProgress = it
//        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
//        viewModel.progressAnswers.observe(viewLifecycleOwner) {
//            tvAnswerProgress.text = it
//        }
    }

//    private fun getColorByState(state: Boolean): Int {
//        val colorResTd = if (state) {
//            android.R.color.holo_green_light
//        } else {
//            android.R.color.holo_red_light
//        }
//        return ContextCompat.getColor(requireContext(), colorResTd)
//    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult))
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}