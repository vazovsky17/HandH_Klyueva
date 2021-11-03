package app.vazovsky.lesson_5_klyueva.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_5_klyueva.R
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Locale

class FourthActivity : AppCompatActivity() {
    companion object {
        const val KEY_TIME = "key_time"

        fun createStartIntent(context: Context, currentTime: Long): Intent {
            return Intent(context, FourthActivity::class.java).apply {
                putExtra(KEY_TIME, currentTime)
            }
        }
    }


    private val textViewCurrentTime by lazy { findViewById<TextView>(R.id.textViewCurrentTime) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        getTime()
        findViewById<MaterialButton>(R.id.buttonToThis).setOnClickListener {
            val currentTime = System.currentTimeMillis()
            startActivity(createStartIntent(this, currentTime))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        getTime()
        setIntent(intent)
    }

    private fun getTime() {
        val currentTimeLong = intent.getLongExtra(KEY_TIME, 0L)
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US)
        val currentTime = sdf.format(currentTimeLong)
        textViewCurrentTime.text = currentTime
    }
}