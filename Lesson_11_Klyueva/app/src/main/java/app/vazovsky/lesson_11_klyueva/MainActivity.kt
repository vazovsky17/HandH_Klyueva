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
            Column("05.05", 30),
            Column("06.05", 20),
            Column("07.05", 60),
            Column("08.05", 46),
            Column("09.05", 46),
            Column("10.05", 30),
            Column("11.05", 20),
            Column("12.05", 60),
            Column("13.05", 46),
            Column("14.05", 46)
        )
        binding.statView.setData(columns)
    }
}