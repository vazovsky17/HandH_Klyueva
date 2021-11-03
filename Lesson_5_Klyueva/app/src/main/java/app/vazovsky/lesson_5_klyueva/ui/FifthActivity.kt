package app.vazovsky.lesson_5_klyueva.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_5_klyueva.R
import app.vazovsky.lesson_5_klyueva.model.Data
import com.google.android.material.button.MaterialButton


class FifthActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_RESULT_INPUT_TEXT = "extra_result_input_text"
        const val KEY_DATA = "key_data"

        fun createStartIntent(context: Context): Intent {
            return Intent(context, FifthActivity::class.java)
        }
    }

    private val editTextInputResult by lazy { findViewById<EditText>(R.id.editTextInputResult) }

    private val editTextData by lazy { findViewById<EditText>(R.id.editTextData) }
    private val textViewData by lazy { findViewById<TextView>(R.id.textViewData) }

    private var data: Data = Data()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

        findViewById<MaterialButton>(R.id.buttonDeliverResult).setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(EXTRA_RESULT_INPUT_TEXT, editTextInputResult.text.toString())
            })
            finish()
        }
        findViewById<MaterialButton>(R.id.buttonSaveData).setOnClickListener {
            data = Data(editTextData.text.toString())
            textViewData.text = data.value
        }
    }

    override fun onResume() {
        super.onResume()
        textViewData.text = data.value ?: "Пусто"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_DATA, data)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        data = savedInstanceState.getParcelable(KEY_DATA)!!
    }
}