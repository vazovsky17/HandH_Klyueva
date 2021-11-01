package app.vazovsky.lesson_4_klyueva.tools

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_4_klyueva.R
import app.vazovsky.lesson_4_klyueva.models.DetailInfoItem
import app.vazovsky.lesson_4_klyueva.models.InfoItem

class BaseItemViewHolder(
    parent: ViewGroup,
    private val onItemClick: (String) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_base_info, parent, false)
) {
    private val textViewBaseTitle by lazy { itemView.findViewById<TextView>(R.id.textViewBaseTitle) }
    private val imageViewBaseImg by lazy { itemView.findViewById<ImageView>(R.id.imageViewBaseImg) }
    private val textViewBaseDesc by lazy { itemView.findViewById<TextView>(R.id.textViewBaseDesc) }

    fun bind(item: InfoItem) {
        itemView.setOnClickListener {
            onItemClick(item.title)
        }

        textViewBaseTitle.text = item.title
        imageViewBaseImg.setImageResource(item.img)

        if (item is DetailInfoItem) {
            textViewBaseDesc.visibility = View.VISIBLE
            textViewBaseDesc.text = item.desc
            textViewBaseDesc.setTextColor(if (item.isImportant) Color.RED else Color.GRAY)
        } else {
            textViewBaseDesc.visibility = View.GONE
            textViewBaseDesc.text = ""
        }
    }
}