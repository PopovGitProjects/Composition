package com.example.composition.presentation.screens.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentGameFinishedBinding == null")

    private lateinit var result: GameResult


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backPressedCallback()
        retryButtonListener()
        bindViews()
    }

    private fun bindViews() = with(binding) {
        emojiResult.setImageResource(getSmileResId())
        tvRequiredAnswer.text = String.format(
            getString(R.string.required_score),
            result.gameSettings.minCountOfRightAnswers
        )
        Log.d("My", "Text: ${tvRequiredAnswer.text}")
        tvScore.text = String.format(
            getString(R.string.score_answer),
            result.countOfRightAnswers
        )
        tvScoreAnswer.text = String.format(
            getString(R.string.require_percentage),
            result.gameSettings.minPercentOfRightAnswers
        )
        tvScorePercentage.text = String.format(
            getString(R.string.score_percentage),
            getPercentOfRightAnswer()
        )
    }

    private fun getPercentOfRightAnswer() = with(result) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    private fun getSmileResId(): Int {
        return if (result.winner) {
            R.drawable.icon_happy
        } else {
            R.drawable.icon_worry
        }
    }

    private fun backPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            callback
        )
    }

    private fun retryButtonListener() = with(binding) {
        btnRetry.setOnClickListener {
            retryGame()
        }
    }


    companion object {
        private const val RESULT_KEY = "result key"
        fun newInstance(result: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RESULT_KEY, result)
                }
            }
        }

    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    private fun parseArgs() {
        result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            requireArguments().getParcelable(RESULT_KEY, GameResult::class.java)!!
        else
            requireArguments().getParcelable(RESULT_KEY)!!
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}