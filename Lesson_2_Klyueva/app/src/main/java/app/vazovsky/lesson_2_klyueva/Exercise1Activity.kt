package app.vazovsky.lesson_2_klyueva

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_2_klyueva.databinding.ActivityExercise1Binding

class Exercise1Activity : AppCompatActivity() {

    private var _binding: ActivityExercise1Binding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityExercise1Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}