package app.vazovsky.lesson_4_klyueva.tools

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_4_klyueva.R
import app.vazovsky.lesson_4_klyueva.models.DetailInfoItem

class DetailItemViewHolder(
    parent: ViewGroup,
    private val onItemClick: (String) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_detail_info, parent, false)
) {
    private val textViewDetailTitle by lazy { itemView.findViewById<TextView>(R.id.textViewDetailTitle) }
    private val textViewDetailDesc by lazy { itemView.findViewById<TextView>(R.id.textViewDetailDesc) }
    private val imageViewDetailImg by lazy { itemView.findViewById<ImageView>(R.id.imageViewDetailImg) }

    fun bind(detailItem: DetailInfoItem) {
        itemView.setOnClickListener {
            onItemClick(detailItem.title)
        }

        textViewDetailTitle.text = detailItem.title
        textViewDetailDesc.text = detailItem.desc
        if (detailItem.isImportant) {
            textViewDetailDesc.setTextColor(Color.RED)
        } else textViewDetailDesc.setTextColor(Color.GRAY)
        imageViewDetailImg.setImageResource(detailItem.img)
    }
}