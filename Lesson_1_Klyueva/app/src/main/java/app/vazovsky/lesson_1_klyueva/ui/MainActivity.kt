package app.vazovsky.lesson_1_klyueva.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import app.vazovsky.lesson_1_klyueva.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn1 = findViewById<Button>(R.id.btnExercise1)
        val btn2 = findViewById<Button>(R.id.btnExercise2)
        btn1.setOnClickListener {
            val intent = FirstExerciseActivity.createStartIntent(this)
            startActivity(intent)
        }
        btn2.setOnClickListener {
            val intent = SecondExerciseActivity.createStartIntent(this)
            startActivity(intent)
        }
    }
}