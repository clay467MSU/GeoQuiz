package com.gall.msu.geoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gall.msu.geoquiz.databinding.ActivityMainBinding
import com.gall.msu.geoquiz.ui.theme.GeoQuizTheme
import com.gall.msu.geoquiz.ui.theme.Question

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {

    private lateinit var binding : ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true, null),
        Question(R.string.question_pacific, false, null),
        Question(R.string.question_dolphins, true, null),
        Question(R.string.question_mars, true, null),
        Question(R.string.question_morocco, false, null)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view: View ->
            onAnswerClicked(true)
        }

        binding.falseButton.setOnClickListener { view: View ->
            onAnswerClicked(false)
        }

        val commonOnClickListener = View.OnClickListener { view: View ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.nextButton.setOnClickListener(commonOnClickListener)
        binding.questionTextview.setOnClickListener(commonOnClickListener)

        binding.prevButton.setOnClickListener { view: View ->
            currentIndex = (currentIndex - 1) % questionBank.size
            validateIndex()
            updateQuestion()
        }

    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    //controller function
    private fun onAnswerClicked(answer: Boolean){
        //store answer
        questionBank[currentIndex].userAnswer = answer
        var isCorrect = questionBank[currentIndex].correctAnswer == questionBank[currentIndex].userAnswer

        val message = if (isCorrect) "Correct" else "Incorrect"
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        )
            .show()
        updateQuestion()
        var isOver = gameEnds()
        if (isOver) resetGame()
    }
    //set question text & enable true/false buttons
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        var isAnswered = questionBank[currentIndex].userAnswer != null
        binding.questionTextview.setText(questionTextResId)
        binding.trueButton.isEnabled = !isAnswered
        binding.falseButton.isEnabled = !isAnswered
    }
    //make sure the index is valid
    private fun validateIndex(){
        val maxIndex = questionBank.size - 1
        if (currentIndex < 0) currentIndex = maxIndex
    }
    //determine if the game has ended. if so, calculate & display score
    private fun gameEnds(): Boolean {
        val isNotOver = questionBank.any { it.userAnswer == null }
        if (!isNotOver) {
            val numCorrectAnswers = questionBank.count { it.correctAnswer == it.userAnswer }
            Log.d("numCorrect", numCorrectAnswers.toString())
            var score = (numCorrectAnswers.toDouble() / questionBank.size) * 100.0
            Log.d("score", String.format("%.1f %%", score))
        }
        return !isNotOver
    }
    //reset answers
    private fun resetGame(){
        for (question in questionBank) {
            question.userAnswer = null
        }
    }
}