@file:Suppress("UNUSED_EXPRESSION")

package com.example.composition.presentation.bindingadapters

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult


interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

@BindingAdapter("resultEmoji")
fun bindingResultEmoji(imageView: ImageView, winner: Boolean) {
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

@BindingAdapter("progressBar")
fun progressBar(progressBar: ProgressBar, count: Int) {
    progressBar.setProgress(count, true)
}

@BindingAdapter("progressBarSec")
fun progressBarSec(progressBar: ProgressBar, count: Int) {
    progressBar.secondaryProgress = count
}

@BindingAdapter("enoughCount")
fun enoughCount(textView: TextView, state: Boolean) {
    textView.setTextColor(getColorByState(textView.context, state))
}

@BindingAdapter("enoughPercent")
fun enoughPercent(progressBar: ProgressBar, state: Boolean) {
    val color = getColorByState(progressBar.context, state)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("numberAsText")
fun numberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun onOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}

private fun getPercentOfRightAnswer(gameResult: GameResult) = with(gameResult) {
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

private fun getColorByState(context: Context, state: Boolean): Int {
    val colorResTd = if (state) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResTd)
}


