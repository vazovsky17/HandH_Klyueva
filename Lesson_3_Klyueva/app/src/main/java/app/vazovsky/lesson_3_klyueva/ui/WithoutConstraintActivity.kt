package app.vazovsky.lesson_3_klyueva.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import app.vazovsky.lesson_3_klyueva.R
import app.vazovsky.lesson_3_klyueva.account

class WithoutConstraintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_without_constraint)

        findViewById<TextView>(R.id.tvCardNumber).text = "Карта №${account.cardNumber}"
        findViewById<TextView>(R.id.tvCardJob).text = account.job
        findViewById<TextView>(R.id.tvName).text = account.name
        findViewById<TextView>(R.id.tvSurname).text = account.surname
        findViewById<TextView>(R.id.tvEmail).text = account.email
        findViewById<TextView>(R.id.tvLogin).text = account.login
        findViewById<TextView>(R.id.tvRegion).text = account.region


        findViewById<TextView>(R.id.tvLogout).setOnClickListener {
            Toast.makeText(this,"Выход", Toast.LENGTH_LONG).show()
        }
        findViewById<ImageButton>(R.id.btnEditRegion).setOnClickListener {
            Toast.makeText(this,"Смена региона", Toast.LENGTH_LONG).show()
        }
        findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            Toast.makeText(this,"К клубу Петровичей", Toast.LENGTH_LONG).show()
        }
        findViewById<Toolbar>(R.id.toolbar).setOnMenuItemClickListener {
            Toast.makeText(this,"Редактирование", Toast.LENGTH_LONG).show()
            true
        }
    }
}