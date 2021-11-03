package app.vazovsky.lesson_5_klyueva.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_5_klyueva.R
import app.vazovsky.lesson_5_klyueva.model.Service
import app.vazovsky.lesson_5_klyueva.tools.ServicesAdapter
import com.bumptech.glide.Glide

class SixthActivity : AppCompatActivity() {

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, SixthActivity::class.java)
        }
    }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val toolbarBackground by lazy { findViewById<ImageView>(R.id.imageViewToolbarBackground) }
    private val listServices = listOf<Service>(
        Service(
            "Царь пышка",
            "Скидка 10% на выпечку \nпо коду",
            "Лермонтовский пр, 52, МО №1",
            "https://mir-s3-cdn-cf.behance.net/projects/max_808/8876507.5479aa2adaa28.jpg",
        ),
        Service(
            "Химчистка «МАЙ?»",
            "Скидка 5% на чистку пальто",
            "Лермонтовский пр, 48",
            "https://vsevolozhsk.esbd.ru/public/img/add/47/93460.jpg",
            true
        ),
        Service(
            "Шаверма У Ашота",
            "При покупке шавермы, фалафель бесплатно",
            "Лермонтовский пр, 52, МО №1",
            "https://static.toiimg.com/photo/imgsize-540493,msid-77318720/77318720.jpg",
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sixth)
        Glide.with(this)
            .load("https://novostroy.kz/Media/images/complex/big/209a1afea0056ac9bf306576806724be.jpg")
            .centerCrop()
            .into(toolbarBackground)

        val adapter = ServicesAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setItems(listServices)
        adapter.onItemClick = { item ->
            Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
        }
        registerForContextMenu(recyclerView)

        findViewById<TextView>(R.id.textViewShowAll).setOnClickListener {
            Toast.makeText(this, "Показать все", Toast.LENGTH_SHORT).show()
        }
        findViewById<TextView>(R.id.textViewOfferService).setOnClickListener {
            Toast.makeText(this, "Предложить услугу", Toast.LENGTH_SHORT).show()
        }
    }

}