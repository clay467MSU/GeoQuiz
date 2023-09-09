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
import com.gall.msu.geoquiz.ui.theme.GeoQuizTheme

class MainActivity : ComponentActivity() {

    private lateinit var trueButton : Button
    private lateinit var falseButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById<Button>(R.id.true_button)
        falseButton = findViewById<Button>(R.id.false_button)

        trueButton.setOnClickListener { view: View ->
            Toast.makeText(
                this,
                R.string.true_button,
                Toast.LENGTH_SHORT
            )
                .show()
        }

        falseButton.setOnClickListener { view: View ->
            Toast.makeText(
                this,
                R.string.false_button,
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

}