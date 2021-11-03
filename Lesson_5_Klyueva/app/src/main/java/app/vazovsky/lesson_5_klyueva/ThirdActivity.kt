package app.vazovsky.lesson_5_klyueva

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ThirdActivity : AppCompatActivity() {
    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, ThirdActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        findViewById<MaterialButton>(R.id.buttonToFirstActivity).setOnClickListener {
            //сделать переход на первую Activity с удалением других активити со стэка
            startActivity(FirstActivity.createStartIntent(this))
        }
        findViewById<MaterialButton>(R.id.buttonToFifthActivity).setOnClickListener {
            startActivity(FifthActivity.createStartIntent(this))
        }
    }
}