package app.vazovsky.lesson_12_klyueva.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import app.vazovsky.lesson_12_klyueva.R
import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import app.vazovsky.lesson_12_klyueva.data.model.Bridge.Companion.STATE_LATE
import app.vazovsky.lesson_12_klyueva.data.model.State
import app.vazovsky.lesson_12_klyueva.databinding.FragmentDetailBinding
import app.vazovsky.lesson_12_klyueva.presentation.CustomViewFlipper.Companion.STATE_DATA
import app.vazovsky.lesson_12_klyueva.presentation.CustomViewFlipper.Companion.STATE_ERROR
import app.vazovsky.lesson_12_klyueva.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by appViewModels()
    private val binding by viewBinding(FragmentDetailBinding::bind)

    companion object {
        const val BRIDGE_ID = "BRIDGE ID"
    }

    private val customViewFlipper get() = binding.customViewFlipper
    private val idBridge by lazy { arguments?.getInt(BRIDGE_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idBridge?.let { viewModel.loadBridgeDetail(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_listFragment)
        }
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            customViewFlipper.setState(state)
            when (customViewFlipper.displayedChild) {
                STATE_DATA -> {
                    val bridge = (state as State.Data<*>).data as Bridge
                    setData(bridge)
                }
                STATE_ERROR -> {
                    customViewFlipper.setOnErrorClickListener {
                        idBridge?.let { viewModel.loadBridgeDetail(it) }
                    }
                }
            }
        }
    }

    private fun setData(bridge: Bridge) {
        binding.apply {
            customViewBridge.bindBridge(bridge)
            textViewBridgeDescription.text = bridge.description
            Glide.with(this@DetailFragment)
                .asBitmap()
                .load(if (bridge.getState() == STATE_LATE) bridge.photoCloseUrl else bridge.photoOpenUrl)
                .centerCrop()
                .into(imageViewToolbarBackground)
        }
    }
}