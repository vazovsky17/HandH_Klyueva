package app.vazovsky.lesson_11_klyueva

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.vazovsky.lesson_11_klyueva.databinding.ActivityMainBinding
import app.vazovsky.lesson_11_klyueva.model.Column
import by.kirich1409.viewbindingdelegate.viewBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val columns = listOf(
            Column("04.05", 46),
            Column("04.05", 30),
            Column("04.05", 20),
            Column("04.05", 60),
            Column("04.05", 46),
            Column("04.05", 46),
            Column("04.05", 30),
            Column("04.05", 20),
            Column("04.05", 60),
            Column("04.05", 46),
            Column("04.05", 46)
        )
        binding.statView.setData(columns)
    }
}