package app.vazovsky.lesson_6_klyueva.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_6_klyueva.R
import app.vazovsky.lesson_6_klyueva.model.ElectroService
import app.vazovsky.lesson_6_klyueva.model.Service
import app.vazovsky.lesson_6_klyueva.model.WaterService
import app.vazovsky.lesson_6_klyueva.tools.services.ServicesAdapter
import com.google.android.material.appbar.MaterialToolbar

class SecondFragment : Fragment(R.layout.fragment_second) {
    companion object {
        fun newInstance(): SecondFragment {
            return SecondFragment()
        }
    }

    private val listOfServices = listOf<Service>(
        WaterService(
            "Холодная вода",
            "54656553",
            R.drawable.ic_water_cold,
            "Подача данных о холодной воде",
            "Необходимо подать показания до 25.03.18",
            isWarning = true
        ),
        WaterService(
            "Горячая вода",
            "54656553",
            R.drawable.ic_water_hot,
            "Подача данных о горячей воде",
            "Необходимо подать показания до 25.03.18",
            isWarning = true
        ),
        ElectroService(
            "Электроэнергия",
            "54656553",
            R.drawable.ic_electro,
            "Подача данных об электроэнергии",
            "Необходимо подать показания до 25.03.18"
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = ServicesAdapter()
        adapter.setItems(listOfServices)
        adapter.onItemClick = { item ->
            Toast.makeText(context, "${item.title} ${item.info}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)



        toolbar.setOnMenuItemClickListener {
            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
            true
        }
    }
}