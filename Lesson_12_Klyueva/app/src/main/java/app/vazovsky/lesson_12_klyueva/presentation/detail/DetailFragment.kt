package app.vazovsky.lesson_12_klyueva.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.vazovsky.lesson_12_klyueva.R
import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import app.vazovsky.lesson_12_klyueva.data.model.Bridge.Companion.STATE_LATE
import app.vazovsky.lesson_12_klyueva.data.model.State
import app.vazovsky.lesson_12_klyueva.databinding.FragmentDetailBinding
import app.vazovsky.lesson_12_klyueva.presentation.CustomViewFlipper.Companion.STATE_DATA
import app.vazovsky.lesson_12_klyueva.presentation.CustomViewFlipper.Companion.STATE_ERROR
import app.vazovsky.lesson_12_klyueva.presentation.base.BaseFragment
import app.vazovsky.lesson_12_klyueva.presentation.list.ListFragmentDirections
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by appViewModels()
    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val args by navArgs<DetailFragmentArgs>()
    private val idBridge by lazy { args.idBridge }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadBridgeDetail(idBridge)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()

        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            binding.customViewFlipper.setState(state)
            if (state is State.Data<*>) {
                val bridge = state.data as Bridge
                setData(bridge)
            }
        }

    }

    private fun configureViews() = with(binding) {
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        customViewFlipper.setOnErrorClickListener {
            viewModel.loadBridgeDetail(idBridge)
        }
    }

    private fun setData(bridge: Bridge) = with(binding) {
        customViewBridge.bindBridge(bridge)
        textViewBridgeDescription.text = bridge.description
        Glide.with(this@DetailFragment)
            .asBitmap()
            .load(if (bridge.getState() == STATE_LATE) bridge.photoCloseUrl else bridge.photoOpenUrl)
            .centerCrop()
            .into(imageViewToolbarBackground)
    }
}