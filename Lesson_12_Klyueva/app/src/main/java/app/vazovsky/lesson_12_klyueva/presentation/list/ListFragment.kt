package app.vazovsky.lesson_12_klyueva.presentation.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
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
import app.vazovsky.lesson_12_klyueva.presentation.detail.DetailFragment.Companion.BRIDGE_ID
import by.kirich1409.viewbindingdelegate.viewBinding

class ListFragment : BaseFragment(R.layout.fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel: ListViewModel by appViewModels()

    companion object {
        const val TAG = "LOL"
    }

    private val recyclerView: RecyclerView
        get() = binding.recyclerView
    private val customViewFlipper: CustomViewFlipper
        get() = binding.customViewFlipper
    private val toolbar: Toolbar
        get() = binding.toolbar
    private val adapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadBridges()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_map -> {
                    findNavController().navigate(R.id.action_listFragment_to_mapFragment)
                    true
                }
                else -> false
            }
        }
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            customViewFlipper.setState(state)
            if (customViewFlipper.displayedChild == STATE_DATA) {
                adapter.setItems((state as State.Data<List<Bridge>>).data)
            } else if (customViewFlipper.displayedChild == STATE_ERROR) {
                customViewFlipper.setOnErrorClickListener {
                    viewModel.loadBridges()
                }
            }
        }
    }

    private fun configureRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.onItemClick = {
            val bundle = bundleOf(BRIDGE_ID to it.id)
            findNavController().navigate(R.id.action_listFragment_to_detailFragment,bundle)
        }
        recyclerView.adapter = adapter
    }

}