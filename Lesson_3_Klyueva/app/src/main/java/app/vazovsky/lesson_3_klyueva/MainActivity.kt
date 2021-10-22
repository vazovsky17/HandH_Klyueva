package app.vazovsky.lesson_3_klyueva

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.vazovsky.lesson_3_klyueva.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWithoutConstraint.setOnClickListener {
            startActivity(Intent(this, WithoutConstraintActivity::class.java))
        }
        binding.btnConstraint.setOnClickListener {
            startActivity(Intent(this, ConstraintActivity::class.java))
        }
        binding.btnCoordinator.setOnClickListener {
            startActivity(Intent(this, CoordinatorActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}