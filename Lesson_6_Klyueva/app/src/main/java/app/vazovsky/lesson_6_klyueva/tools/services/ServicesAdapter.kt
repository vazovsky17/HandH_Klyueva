package app.vazovsky.lesson_6_klyueva.tools.services

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_6_klyueva.model.ElectroService
import app.vazovsky.lesson_6_klyueva.model.Service
import app.vazovsky.lesson_6_klyueva.model.WaterService

class ServicesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_WATER_SERVICE = 0
        const val TYPE_ELECTRO_SERVICE = 1
    }

    private val items = mutableListOf<Service>()

    fun setItems(items: List<Service>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    lateinit var onItemClick: (Service) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_WATER_SERVICE -> WaterServiceViewHolder(parent, onItemClick)
            TYPE_ELECTRO_SERVICE -> ElectroServiceViewHolder(parent, onItemClick)
            else -> throw Exception("Unsupported View Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_WATER_SERVICE -> (holder as WaterServiceViewHolder).bind(items[position] as WaterService)
            TYPE_ELECTRO_SERVICE -> (holder as ElectroServiceViewHolder).bind(items[position] as ElectroService)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is WaterService) {
            TYPE_WATER_SERVICE
        } else {
            TYPE_ELECTRO_SERVICE
        }
    }

}