package app.vazovsky.lesson_12_klyueva.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ViewFlipper
import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import app.vazovsky.lesson_12_klyueva.data.model.State
import app.vazovsky.lesson_12_klyueva.databinding.CustomViewFlipperBinding

class CustomViewFlipper : ViewFlipper {
    companion object {
        const val STATE_LOADING = 0
        const val STATE_ERROR = 1
        const val STATE_DATA = 2
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    private val binding = CustomViewFlipperBinding.inflate(LayoutInflater.from(context), this)

    private fun init(context: Context, attrs: AttributeSet?) {}

    fun setOnErrorClickListener(callback: () -> Unit) {
        binding.buttonReloading.setOnClickListener {
            callback()
        }
    }

    fun setState(state: State<*>) {
        when (state) {
            is State.Loading -> displayedChild = STATE_LOADING
            is State.Error -> {
                displayedChild = STATE_ERROR
                binding.textViewError.text = state.error.message
            }
            is State.Data<*> -> {
                when (state.data) {
                    is Bridge -> {
                        displayedChild = STATE_DATA
                    }
                    is List<*> -> {
                        if (state.data.isEmpty()) {
                            displayedChild = STATE_ERROR
                            binding.textViewError.text = "Пусто"
                        } else {
                            displayedChild = STATE_DATA
                        }
                    }
                }
            }
        }
    }
}