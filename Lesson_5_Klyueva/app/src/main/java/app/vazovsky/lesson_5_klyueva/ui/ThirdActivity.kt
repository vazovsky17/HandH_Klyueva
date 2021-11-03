package app.vazovsky.lesson_5_klyueva.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_5_klyueva.R
import app.vazovsky.lesson_5_klyueva.tools.FifthActivityContract
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class ThirdActivity : AppCompatActivity() {
    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, ThirdActivity::class.java)
        }
    }

    private val launcher = registerForActivityResult(FifthActivityContract()) { inputText ->
        Snackbar.make(findViewById(R.id.frameLayout), inputText, Snackbar.LENGTH_SHORT).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        findViewById<MaterialButton>(R.id.buttonToFirstActivity).setOnClickListener {
            startActivity(FirstActivity.createStartIntent(this))
        }
        findViewById<MaterialButton>(R.id.buttonToFifthActivity).setOnClickListener {
            launcher.launch("")
        }
        // Вторая кнопка для обычного перехода, без результата
        findViewById<MaterialButton>(R.id.buttonToFifthActivity2).setOnClickListener {
            startActivity(FifthActivity.createStartIntent(this))
        }
    }
}