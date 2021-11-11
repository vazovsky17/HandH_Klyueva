package app.vazovsky.lesson_7_klyueva.presentation.bridgelist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_7_klyueva.data.model.Bridge

class BridgeListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Bridge>()
    override fun getItemCount() = items.size
    fun setItems(items: List<Bridge>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    lateinit var onItemClick: (Bridge) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BridgeViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BridgeViewHolder).bind(items[position])
    }
}