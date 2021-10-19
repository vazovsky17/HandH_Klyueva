package app.vazovsky.lesson_2_klyueva

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.vazovsky.lesson_2_klyueva.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val mBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnToExercise1.setOnClickListener {
            startActivity(Intent(this, Exercise1Activity::class.java))
        }
        mBinding.btnToExercise2.setOnClickListener {
            startActivity(Intent(this, Exercise2Activity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}