package app.vazovsky.lesson_1_klyueva.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_1_klyueva.R

class FirstExerciseActivity : AppCompatActivity() {
    companion object {
        fun createStartIntent(context: Context) = Intent(context, FirstExerciseActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_exercise)

        val etNames = findViewById<EditText>(R.id.etNames)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnPrint = findViewById<Button>(R.id.btnPrint)
        val tvNamesList = findViewById<TextView>(R.id.tvNamesList)

        val namesArray = arrayListOf<String>()

        btnSave.setOnClickListener {
            namesArray.addAll(etNames.text.toString().split(" "))
        }
        btnPrint.setOnClickListener {
            val namesSet = namesArray.toSortedSet()
            val stringBuilder = StringBuilder()
            for (name in namesSet) {
                stringBuilder.append(name + "\n")
            }
            tvNamesList.text = stringBuilder
        }
    }


}