package com.gall.msu.geoquiz

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.activity.ComponentActivity
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater = result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateQuestion()

        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.correctAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

        binding.trueButton.setOnClickListener { view: View ->
            onAnswerClicked(true)
        }

        binding.falseButton.setOnClickListener { view: View ->
            onAnswerClicked(false)
        }

        val commonOnClickListener = View.OnClickListener { view: View ->
            quizViewModel.nextQuestion()
            updateQuestion()
        }

        binding.nextButton.setOnClickListener(commonOnClickListener)
        binding.questionTextview.setOnClickListener(commonOnClickListener)

        binding.prevButton.setOnClickListener { view: View ->
            quizViewModel.prevQuestion()
            quizViewModel.validateIndex()
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
        quizViewModel.userAnswer = answer
        var isCorrect = quizViewModel.correctAnswer == answer

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
        val questionTextResId = quizViewModel.questionText
        var isAnswered = quizViewModel.isAnswered
        binding.questionTextview.setText(questionTextResId)
        binding.trueButton.isEnabled = !isAnswered
        binding.falseButton.isEnabled = !isAnswered
    }

    //determine if the game has ended. if so, calculate & display score
    private fun gameEnds(): Boolean {
        val isNotOver = quizViewModel.isNotOver
        if (!isNotOver) {
            val numCorrectAnswers = quizViewModel.numCorrectAnswers
            var score = quizViewModel.score

            Toast.makeText(
                this,
                "Final Score: " + String.format("%.1f %%", score),
                Toast.LENGTH_SHORT
            )
                .show()
        }
        return !isNotOver
    }
    //reset answers
    private fun resetGame(){
        quizViewModel.resetQuestions()
        updateQuestion()
    }
}