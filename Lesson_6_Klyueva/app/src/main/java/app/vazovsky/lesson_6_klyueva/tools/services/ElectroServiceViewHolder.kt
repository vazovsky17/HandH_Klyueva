package app.vazovsky.lesson_6_klyueva.tools.services

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_6_klyueva.R
import app.vazovsky.lesson_6_klyueva.model.ElectroService
import app.vazovsky.lesson_6_klyueva.model.Service

class ElectroServiceViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Service) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_electro_service, parent, false)
) {
    private val textViewServiceTitle by lazy { itemView.findViewById<TextView>(R.id.textViewServiceTitle) }
    private val imageViewServiceIcon by lazy { itemView.findViewById<ImageView>(R.id.imageViewServiceIcon) }
    private val textViewServiceCode by lazy { itemView.findViewById<TextView>(R.id.textViewServiceCode) }
    private val editTextServiceNewDayData by lazy { itemView.findViewById<EditText>(R.id.editTextServiceNewDayData) }
    private val editTextServiceNewNightData by lazy { itemView.findViewById<EditText>(R.id.editTextServiceNewNightData) }
    private val editTextServiceNewPickData by lazy { itemView.findViewById<EditText>(R.id.editTextServiceNewPickData) }
    private val buttonServiceInfo by lazy { itemView.findViewById<ImageButton>(R.id.buttonServiceInfo) }
    private val buttonServiceContextMenu by lazy { itemView.findViewById<ImageButton>(R.id.buttonServiceContextMenu) }
    private val buttonServiceSendData by lazy { itemView.findViewById<ImageButton>(R.id.buttonServiceSendData) }
    private val textViewServiceDescription by lazy { itemView.findViewById<TextView>(R.id.textViewServiceDescription) }

    fun bind(service: ElectroService) {
        itemView.setOnClickListener {
            onItemClick(service)
        }

        textViewServiceTitle.text = service.title
        imageViewServiceIcon.setImageResource(service.icon)
        textViewServiceCode.text = service.code

        buttonServiceInfo.setOnClickListener {
            Toast.makeText(itemView.context, service.info, Toast.LENGTH_SHORT).show()
        }
        buttonServiceContextMenu.setOnClickListener {
            Toast.makeText(itemView.context, "Контекстное меню для ${service.title}", Toast.LENGTH_SHORT).show()
        }
        buttonServiceSendData.setOnClickListener {
            try {
                service.newDayData = editTextServiceNewDayData.text.toString().toInt()
                service.newNightData = editTextServiceNewNightData.text.toString().toInt()
                service.newPickData = editTextServiceNewPickData.text.toString().toInt()
                Toast.makeText(
                    itemView.context,
                    "Новые показания для ${service.title}\nДень: ${service.newDayData}\nНочь: ${service.newNightData}\nПик:${service.newPickData}",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: NumberFormatException) {
                Toast.makeText(itemView.context, "Введите данные числами!!", Toast.LENGTH_SHORT).show()
            } finally {
                editTextServiceNewDayData.text.clear()
                editTextServiceNewNightData.text.clear()
                editTextServiceNewPickData.text.clear()
            }
        }
        textViewServiceDescription.text = service.desc
        textViewServiceDescription.setTextColor(if (service.isWarning) Color.RED else Color.GRAY)
    }
}