package app.vazovsky.lesson_5_klyueva.tools

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_5_klyueva.model.Service

class ServicesAdapter : RecyclerView.Adapter<ServiceViewHolder>() {

    private val items = mutableListOf<Service>()
    lateinit var onItemClick: (Service) -> Unit

    fun setItems(items: List<Service>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}