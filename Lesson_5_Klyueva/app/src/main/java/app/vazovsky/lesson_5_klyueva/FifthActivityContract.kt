package app.vazovsky.lesson_5_klyueva

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import app.vazovsky.lesson_5_klyueva.ui.FifthActivity
import app.vazovsky.lesson_5_klyueva.ui.ThirdActivity

class FifthActivityContract : ActivityResultContract<String, String>() {
    override fun createIntent(context: Context, input: String): Intent {
        return FifthActivity.createStartIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        return intent?.getStringExtra(FifthActivity.EXTRA_RESULT_INPUT_TEXT).orEmpty()
    }
}