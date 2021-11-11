package app.vazovsky.lesson_7_klyueva.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_7_klyueva.R
import app.vazovsky.lesson_7_klyueva.data.model.Bridge

class BridgeViewHolder(
    val parent: ViewGroup,
    private val onItemClick: (Bridge) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_bridge, parent, false)
) {

    private val imageViewBridgeState by lazy { itemView.findViewById<ImageView>(R.id.imageViewBridgeState) }
    private val textViewBridgeName by lazy { itemView.findViewById<TextView>(R.id.textViewBridgeName) }
    private val textViewBridgeDivorce by lazy { itemView.findViewById<TextView>(R.id.textViewBridgeDivorce) }
    private val imageButtonBridgePush by lazy { itemView.findViewById<ImageButton>(R.id.imageButtonBridgePush) }

    fun bind(bridge: Bridge) {
        itemView.setOnClickListener {
            onItemClick(bridge)
        }
        textViewBridgeName.text = bridge.name
        textViewBridgeDivorce.text = buildString {
            for (divorce in bridge.divorces) {
                append("${divorce.start} - ${divorce.end}\t\t")
            }
        }
        val state = bridge.getState()
        imageViewBridgeState.setImageResource(state)
        imageButtonBridgePush.setOnClickListener {
            //продумать изменение тинта
            if(bridge.isPushing){
                bridge.isPushing = false
                Toast.makeText(parent.context, "Вы отписались от умедомлений!", Toast.LENGTH_SHORT).show()
            } else {
                bridge.isPushing = true
                Toast.makeText(parent.context, "Вы подписались на умедомления!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}