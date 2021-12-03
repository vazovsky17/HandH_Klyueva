package app.vazovsky.lesson_12_klyueva.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import app.vazovsky.lesson_12_klyueva.databinding.CustomViewBridgeBinding

class CustomViewBridge : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private val binding = CustomViewBridgeBinding.inflate(LayoutInflater.from(context), this)

    private fun init(context: Context, attrs: AttributeSet?) {

    }

    fun bindBridge(bridge: Bridge) {
        binding.apply {
            textViewBridgeName.text = bridge.name
            textViewBridgeDivorce.text = bridge.getDivorceString()
            val state = bridge.getState()
            imageViewBridgeState.setImageResource(state)
        }
    }
}