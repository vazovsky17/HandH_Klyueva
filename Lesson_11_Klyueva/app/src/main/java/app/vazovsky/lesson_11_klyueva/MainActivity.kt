package app.vazovsky.lesson_11_klyueva

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.vazovsky.lesson_11_klyueva.databinding.ActivityMainBinding
import app.vazovsky.lesson_11_klyueva.model.ColumnWithDate
import by.kirich1409.viewbindingdelegate.viewBinding
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val columns = listOf(
            ColumnWithDate(formatStringToDate("05/05/2021"), 46),
            ColumnWithDate(formatStringToDate("06/05/2021"), 30),
            ColumnWithDate(formatStringToDate("07/05/2021"), 20),
            ColumnWithDate(formatStringToDate("08/05/2021"), 100),
            ColumnWithDate(formatStringToDate("09/05/2021"), 74),
            ColumnWithDate(formatStringToDate("10/05/2021"), 22),

            )
        binding.statView.apply {
            setData(columns)
            setOnClickListener {
                this.startMyAnimation()
            }
        }

    }

    /**
     * Форматирование строки в объект Date.
     */
    private fun formatStringToDate(str: String): Date {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.parse(str)
    }
}