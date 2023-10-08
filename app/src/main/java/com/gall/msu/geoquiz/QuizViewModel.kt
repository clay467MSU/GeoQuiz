package com.gall.msu.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.gall.msu.geoquiz.ui.theme.Question

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true, null),
        Question(R.string.question_pacific, false, null),
        Question(R.string.question_dolphins, true, null),
        Question(R.string.question_mars, true, null),
        Question(R.string.question_morocco, false, null)
    )

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    var userAnswer: Boolean = false
        set(answer) {
            questionBank[currentIndex].userAnswer = answer
        }

    val correctAnswer: Boolean
        get() = questionBank[currentIndex].correctAnswer

    val questionText: Int
        get() = questionBank[currentIndex].textResId

    val isAnswered: Boolean
        get() = questionBank[currentIndex].userAnswer != null

    val isNotOver: Boolean
        get() = questionBank.any { it.userAnswer == null }

    val numCorrectAnswers: Int
        get() = questionBank.count { it.correctAnswer == it.userAnswer }

    val score: Double
        get() = (numCorrectAnswers.toDouble() / questionBank.size) * 100.0

    fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun prevQuestion() {
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    //make sure the index is valid
    fun validateIndex(){
        val maxIndex = questionBank.size - 1
        if (currentIndex < 0) currentIndex = maxIndex
    }

    fun resetQuestions(){
        for (question in questionBank) {
            question.userAnswer = null
        }
        currentIndex = 0
    }
}