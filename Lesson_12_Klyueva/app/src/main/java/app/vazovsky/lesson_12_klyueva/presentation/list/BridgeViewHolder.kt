package app.vazovsky.lesson_12_klyueva.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import app.vazovsky.lesson_12_klyueva.databinding.BridgeItemBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import app.vazovsky.lesson_12_klyueva.R


class BridgeViewHolder(
    private val parent: ViewGroup,
    private val onItemClick: (Bridge) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.bridge_item, parent, false)
) {
    private val binding by viewBinding(BridgeItemBinding::bind)

    fun bind(bridge: Bridge) {
        itemView.setOnClickListener { onItemClick(bridge) }
        binding.customViewBridge.bindBridge(bridge)
    }
}