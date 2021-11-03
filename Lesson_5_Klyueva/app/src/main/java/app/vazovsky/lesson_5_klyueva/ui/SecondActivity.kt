package app.vazovsky.lesson_5_klyueva.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_5_klyueva.R
import com.google.android.material.button.MaterialButton

class SecondActivity : AppCompatActivity() {
    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, SecondActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<MaterialButton>(R.id.buttonToThirdActivity).setOnClickListener {
            startActivity(ThirdActivity.createStartIntent(this))
        }
    }
}