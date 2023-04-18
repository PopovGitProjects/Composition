@file:Suppress("UNUSED_EXPRESSION")

package com.example.composition.presentation.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

@BindingAdapter("resultEmoji")
fun bindingResultEmoji(imageView: ImageView, winner: Boolean){
    imageView.setImageResource(getSmileResId(winner))
}

@BindingAdapter("requiredAnswers")
fun bindingRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("scoreAnswer")
fun bindingScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answer),
        count
    )
}

@BindingAdapter("minPercentage")
fun bindingMinPercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.require_percentage),
        count
    )
}
@BindingAdapter("scorePercentage")
fun bindingScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answer),
        getPercentOfRightAnswer(gameResult)
    )
}
private fun getPercentOfRightAnswer(gameResult: GameResult) = with(gameResult){
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}
private fun getSmileResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.icon_happy
    } else {
        R.drawable.icon_worry
    }
}

