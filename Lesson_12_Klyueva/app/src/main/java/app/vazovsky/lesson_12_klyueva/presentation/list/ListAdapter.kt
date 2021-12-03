package app.vazovsky.lesson_12_klyueva.presentation.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_12_klyueva.data.model.Bridge

class ListAdapter : RecyclerView.Adapter<BridgeViewHolder>() {
    private val items = mutableListOf<Bridge>()
    lateinit var onItemClick: (Bridge) -> Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BridgeViewHolder {
        return BridgeViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: BridgeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<Bridge>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }
}