package com.example.composition.presentation.screens.fragments

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.Level
import com.example.composition.presentation.viewmodels.GameViewModel


class GameFragment : Fragment() {

    private lateinit var level: Level
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }
    private val tvOptions by lazy {
        with(binding) {
            mutableListOf<TextView>().apply {
                add(tvOption1)
                add(tvOption2)
                add(tvOption3)
                add(tvOption4)
                add(tvOption5)
                add(tvOption6)
            }
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startGame(level)
        observeViewModel()
        setClickListenersToOptions()
    }

    private fun setClickListenersToOptions() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() = with(binding) {
        viewModel.question.observe(viewLifecycleOwner) {
            tvSum.text = it.sum.toString()
            tvVisibleNum.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        }
        viewModel.percentOfRightAnswer.observe(viewLifecycleOwner) {
            progressBar.setProgress(it, true)
        }
        viewModel.enoughCount.observe(viewLifecycleOwner) {
            tvAnswerProgress.setTextColor(getColorByState(it))
        }
        viewModel.enoughPercent.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            tvTime.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner) {
            progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            tvAnswerProgress.text = it
        }
    }

    private fun getColorByState(state: Boolean): Int {
        val colorResTd = if (state) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResTd)
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_holder, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    private fun parseArgs() {
        level = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            requireArguments().getParcelable(KEY_LVL, Level::class.java)!!
        else
            requireArguments().getParcelable(KEY_LVL)!!
    }

    companion object {
        const val KEY_LVL = "level"
        const val NAME = "GameFragment"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LVL, level)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}