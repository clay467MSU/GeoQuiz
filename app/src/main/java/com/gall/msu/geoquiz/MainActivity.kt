package com.gall.msu.geoquiz

import android.os.Bundle
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

class MainActivity : ComponentActivity() {

    private lateinit var binding : ActivityMainBinding
    //private lateinit var trueButton : Button
    //private lateinit var falseButton : Button

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_pacific, false),
        Question(R.string.question_dolphins, true),
        Question(R.string.question_mars, true),
        Question(R.string.question_morocco, false)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view: View ->
            val thisAnswer = true
            val correctAnswer = questionBank[currentIndex].answer
            val message = if (thisAnswer == correctAnswer) "Correct" else "Incorrect"
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
            )
                .show()
        }

        binding.falseButton.setOnClickListener { view: View ->
            val thisAnswer = false
            val correctAnswer = questionBank[currentIndex].answer
            val message = if (thisAnswer == correctAnswer) "Correct" else "Incorrect"
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
            )
                .show()
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
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextview.setText(questionTextResId)
    }
    private fun validateIndex(){
        val maxIndex = questionBank.size - 1
        if (currentIndex < 0) currentIndex = maxIndex
    }
}