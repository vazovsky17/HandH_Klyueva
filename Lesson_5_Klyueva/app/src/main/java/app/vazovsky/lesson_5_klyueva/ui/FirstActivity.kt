package app.vazovsky.lesson_5_klyueva.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_5_klyueva.R
import com.google.android.material.button.MaterialButton

class FirstActivity : AppCompatActivity() {

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, FirstActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        findViewById<MaterialButton>(R.id.buttonToSecondActivity).setOnClickListener {
            startActivity(SecondActivity.createStartIntent(this))
        }
        findViewById<MaterialButton>(R.id.buttonToFourthActivity).setOnClickListener {
            val currentTime = System.currentTimeMillis()
            startActivity(FourthActivity.createStartIntent(this, currentTime))
        }
        findViewById<MaterialButton>(R.id.buttonToSixthActivity).setOnClickListener {
            startActivity(SixthActivity.createStartIntent(this))
        }

    }

}