package app.vazovsky.lesson_4_klyueva.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_4_klyueva.R
import app.vazovsky.lesson_4_klyueva.models.BaseInfoItem
import app.vazovsky.lesson_4_klyueva.models.DetailInfoItem
import app.vazovsky.lesson_4_klyueva.models.InfoItem
import app.vazovsky.lesson_4_klyueva.tools.InfoAdapter
import app.vazovsky.lesson_4_klyueva.tools.ItemDecorator
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.toolbar) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val list = listOf<InfoItem>(
        DetailInfoItem("Квитанции", "- 40 080,55 ₽", R.drawable.ic_bill, true),
        DetailInfoItem("Счетчики", "Подайте показания", R.drawable.ic_counter, true),
        DetailInfoItem("Рассрочка", "Сл. платеж 25.02.2018", R.drawable.ic_installment),
        DetailInfoItem("Страхование", "Полис до 12.01.2019", R.drawable.ic_insurance),
        DetailInfoItem("Интернет и ТВ", "Баланс 350 ₽", R.drawable.ic_tv),
        DetailInfoItem("Домофон", "Подключен", R.drawable.ic_homephone),
        DetailInfoItem("Охрана", "Нет", R.drawable.ic_guard),
        BaseInfoItem("Контакты УК и служб", R.drawable.ic_uk_contacts),
        BaseInfoItem("Мои заявки", R.drawable.ic_request),
        BaseInfoItem("Памятка жителя А101", R.drawable.ic_about)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = InfoAdapter()
        recyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int) = if (position < 6) 1 else 2
        }
        recyclerView.addItemDecoration(ItemDecorator())
        recyclerView.layoutManager = layoutManager
        adapter.setItems(list)
        adapter.onItemClick = { itemTitle ->
            Snackbar.make(recyclerView, itemTitle, Snackbar.LENGTH_SHORT).show()
        }

        toolbar.apply {
            setNavigationOnClickListener {

            }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_info -> {
                        val alertDialog = AlertDialog.Builder(this@MainActivity)
                            .setTitle("Нажми OK")
                            .setPositiveButton("OK") { _, _ ->
                                Toast.makeText(
                                    applicationContext,
                                    "OK", Toast.LENGTH_SHORT
                                ).show()
                            }
                            .create()
                            .show()
                        true
                    }
                    R.id.menu_home -> {
                        Toast.makeText(applicationContext, "Кнопка Home", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> true
                }
            }
        }
    }

}