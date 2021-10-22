package app.vazovsky.lesson_2_klyueva

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_2_klyueva.databinding.ActivityExercise2Binding

class Exercise2Activity : AppCompatActivity() {

    private var _binding: ActivityExercise2Binding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityExercise2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}