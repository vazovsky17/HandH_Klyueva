package app.vazovsky.lesson_1_klyueva.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_1_klyueva.R
import app.vazovsky.lesson_1_klyueva.Student
import app.vazovsky.lesson_1_klyueva.toStudent
import java.security.Key
import java.util.UUID

class SecondExerciseActivity : AppCompatActivity() {
    companion object {
        fun createStartIntent(context: Context) =
            Intent(context, SecondExerciseActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_exercise)

        val etInfo = findViewById<EditText>(R.id.etInfo)
        val btnPrintInfo = findViewById<Button>(R.id.btnPrintInfo)
        val tvInfoList = findViewById<TextView>(R.id.tvInfoList)

        val studentsMap = hashMapOf<String, Student>()

        etInfo.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val student = etInfo.text.toString().toStudent()
                studentsMap[student.id] = student
                etInfo.text.clear()
                return@setOnKeyListener true
            }
            false
        }

        btnPrintInfo.setOnClickListener {
            val stringBuilder = StringBuilder()
            for (student in studentsMap.keys) {
                stringBuilder.append(studentsMap[student].toString() + "\n")
            }
            tvInfoList.text = stringBuilder
        }
    }
}

