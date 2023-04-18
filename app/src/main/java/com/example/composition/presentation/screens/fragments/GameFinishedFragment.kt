package com.example.composition.presentation.screens.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentGameFinishedBinding == null")


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
        retryButtonListener()
        bindViews()
    }

    private fun bindViews() = with(binding) {
        with(args.result) {
            emojiResult.setImageResource(getSmileResId())
            tvRequiredAnswer.text = String.format(
                getString(R.string.required_score),
                gameSettings.minCountOfRightAnswers,
            )
            Log.d("My", "Text: ${tvRequiredAnswer.text}")
            tvScore.text = String.format(
                getString(R.string.score_answer),
                countOfRightAnswers
            )
            tvScoreAnswer.text = String.format(
                getString(R.string.require_percentage),
                gameSettings.minPercentOfRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPercentOfRightAnswer()
            )
        }

    }

    private fun getPercentOfRightAnswer() = with(args.result) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    private fun getSmileResId(): Int {
        return if (args.result.winner) {
            R.drawable.icon_happy
        } else {
            R.drawable.icon_worry
        }
    }


    private fun retryButtonListener() = with(binding) {
        btnRetry.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}