package app.vazovsky.lesson_4_klyueva.tools

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_4_klyueva.models.BaseInfoItem
import app.vazovsky.lesson_4_klyueva.models.DetailInfoItem
import app.vazovsky.lesson_4_klyueva.models.InfoItem

class InfoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val BASE_TYPE = 0
        const val DETAIL_TYPE = 1
        const val DETAIL_TYPE_HORIZONTAL = 2
    }

    private val items = mutableListOf<InfoItem>()

    fun setItems(items: List<InfoItem>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    lateinit var onItemClick: (String) -> Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BASE_TYPE -> BaseItemViewHolder(parent, onItemClick)
            DETAIL_TYPE -> DetailItemViewHolder(parent, onItemClick)
            DETAIL_TYPE_HORIZONTAL -> BaseItemViewHolder(parent, onItemClick)
            else -> throw Exception("Unsupported View Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            BASE_TYPE -> (holder as BaseItemViewHolder).bind(items[position] as BaseInfoItem)
            DETAIL_TYPE -> (holder as DetailItemViewHolder).bind(items[position] as DetailInfoItem)
            DETAIL_TYPE_HORIZONTAL -> (holder as BaseItemViewHolder).bind(items[position] as DetailInfoItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is BaseInfoItem) {
            BASE_TYPE
        } else {
            if (position == 6) {
                DETAIL_TYPE_HORIZONTAL
            } else {
                DETAIL_TYPE
            }
        }
    }
}