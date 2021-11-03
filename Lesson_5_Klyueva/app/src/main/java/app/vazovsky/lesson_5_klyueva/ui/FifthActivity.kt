package app.vazovsky.lesson_5_klyueva.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_5_klyueva.R
import com.google.android.material.button.MaterialButton

class FifthActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_RESULT_INPUT_TEXT = "extra_result_input_text"

        fun createStartIntent(context: Context): Intent {
            return Intent(context, FifthActivity::class.java)
        }
    }

    private val editTextInputResult by lazy { findViewById<EditText>(R.id.editTextInputResult) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

        findViewById<MaterialButton>(R.id.buttonDeliverResult).setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(EXTRA_RESULT_INPUT_TEXT, editTextInputResult.text.toString())
            })
            finish()
        }
    }
}