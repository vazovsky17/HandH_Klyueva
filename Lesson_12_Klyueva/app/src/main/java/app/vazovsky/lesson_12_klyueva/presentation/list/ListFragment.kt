package app.vazovsky.lesson_12_klyueva.presentation.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.ViewFlipper
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_12_klyueva.R
import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import app.vazovsky.lesson_12_klyueva.data.model.State
import app.vazovsky.lesson_12_klyueva.databinding.FragmentListBinding
import app.vazovsky.lesson_12_klyueva.presentation.CustomViewFlipper
import app.vazovsky.lesson_12_klyueva.presentation.CustomViewFlipper.Companion.STATE_DATA
import app.vazovsky.lesson_12_klyueva.presentation.CustomViewFlipper.Companion.STATE_ERROR
import app.vazovsky.lesson_12_klyueva.presentation.base.BaseFragment
import app.vazovsky.lesson_12_klyueva.presentation.map.MapFragmentDirections
import by.kirich1409.viewbindingdelegate.viewBinding
import javax.inject.Inject

class ListFragment : BaseFragment(R.layout.fragment_list) {


    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel: ListViewModel by appViewModels()

    private val customViewFlipper get() = binding.customViewFlipper
    @Inject lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadBridges()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        configureViews()

        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            customViewFlipper.setState(state)
            if (customViewFlipper.displayedChild == STATE_DATA) {
                val items = (state as State.Data<*>).data as List<Bridge>
                adapter.setItems(items)
            }
        }
    }

    private fun configureViews() = with(binding) {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_map -> {
                    findNavController().navigate(ListFragmentDirections.listFragmentToMapFragment())
                    true
                }
                else -> false
            }
        }
        customViewFlipper.setOnErrorClickListener {
            viewModel.loadBridges()
        }
    }

    private fun configureRecyclerView() = with(binding) {
        adapter.onItemClick = { bridge ->
            bridge.id?.let { id ->
                findNavController().navigate(ListFragmentDirections.listFragmentToDetailFragment(id))
            }
        }
        recyclerView.adapter = adapter
    }

}