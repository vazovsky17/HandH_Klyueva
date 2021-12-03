package app.vazovsky.lesson_12_klyueva.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import app.vazovsky.lesson_12_klyueva.databinding.BridgeItemBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import androidx.core.graphics.drawable.DrawableCompat

import android.annotation.SuppressLint

import android.content.res.ColorStateList
import android.util.Log
import android.widget.ImageView
import app.vazovsky.lesson_12_klyueva.R
import app.vazovsky.lesson_12_klyueva.presentation.list.ListFragment.Companion.TAG


class BridgeViewHolder(
    val parent: ViewGroup,
    private val onItemClick: (Bridge) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.bridge_item, parent, false)
) {
    private val binding by viewBinding(BridgeItemBinding::bind)

    fun bind(bridge: Bridge) {
        itemView.setOnClickListener { onItemClick(bridge) }
        binding.apply {
            textViewBridgeName.text = bridge.name
            textViewBridgeDivorce.text = bridge.getDivorceString()
            val state = bridge.getState()
            imageViewBridgeState.setImageResource(state)
        }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    fun setPushingColorTint(imageView: ImageView, color: Int) {
        val colours: ColorStateList = imageView.resources
            .getColorStateList(color)
        val d = DrawableCompat.wrap(imageView.drawable)
        DrawableCompat.setTintList(d, colours)
        imageView.setImageDrawable(d)
    }
}