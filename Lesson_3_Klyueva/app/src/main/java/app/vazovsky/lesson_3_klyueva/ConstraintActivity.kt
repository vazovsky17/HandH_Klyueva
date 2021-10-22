package app.vazovsky.lesson_3_klyueva

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_3_klyueva.databinding.ActivityConstraintBinding

class ConstraintActivity : AppCompatActivity() {
    class WithoutConstraintActivity : AppCompatActivity() {
        private var _binding: ActivityConstraintBinding? = null
        private val binding
            get() = _binding!!

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            _binding = ActivityConstraintBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }

        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
    }
}