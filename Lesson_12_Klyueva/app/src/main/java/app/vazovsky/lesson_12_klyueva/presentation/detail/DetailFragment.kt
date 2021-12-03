package app.vazovsky.lesson_12_klyueva.presentation.detail

import android.os.Bundle
import android.system.Os.bind
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import app.vazovsky.lesson_12_klyueva.R
import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import app.vazovsky.lesson_12_klyueva.data.model.Bridge.Companion.STATE_LATE
import app.vazovsky.lesson_12_klyueva.data.model.State
import app.vazovsky.lesson_12_klyueva.databinding.FragmentDetailBinding
import app.vazovsky.lesson_12_klyueva.presentation.CustomViewFlipper
import app.vazovsky.lesson_12_klyueva.presentation.base.BaseFragment
import app.vazovsky.lesson_12_klyueva.presentation.list.ListFragment.Companion.TAG
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide

class DetailFragment : BaseFragment(R.layout.fragment_detail) {
    private val viewModel: DetailViewModel by appViewModels()
    private val binding by viewBinding(FragmentDetailBinding::bind)

    companion object {
        const val BRIDGE_ID = "BRIDGE ID"
    }

    private val customViewFlipper: CustomViewFlipper
        get() = binding.customViewFlipper
    private val toolbar: Toolbar
        get() = binding.toolbar
    private val idBridge by lazy {
        arguments?.getInt(BRIDGE_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idBridge?.let { viewModel.loadBridgeDetail(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_listFragment)
        }
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            customViewFlipper.setState(state)
            if (customViewFlipper.displayedChild == CustomViewFlipper.STATE_DATA) {
                binding.apply {
                    val bridge = (state as State.Data<*>).data as Bridge
                    customViewBridge.bindBridge(bridge)
                    textViewBridgeDescription.text = bridge.description

                    Glide.with(this@DetailFragment)
                        .asBitmap()
                        .load(if (bridge.getState() == STATE_LATE) bridge.photoCloseUrl else bridge.photoOpenUrl)
                        .centerCrop()
                        .into(imageViewToolbarBackground)
                }

            } else if (customViewFlipper.displayedChild == CustomViewFlipper.STATE_ERROR) {
                customViewFlipper.setOnErrorClickListener {
                    idBridge?.let { viewModel.loadBridgeDetail(it) }
                }
            }
        }
    }
}