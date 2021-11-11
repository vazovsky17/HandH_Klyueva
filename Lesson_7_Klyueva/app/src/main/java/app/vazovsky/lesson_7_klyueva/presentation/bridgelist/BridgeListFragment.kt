package app.vazovsky.lesson_7_klyueva.presentation.bridgelist

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_7_klyueva.R
import app.vazovsky.lesson_7_klyueva.data.model.Bridge
import app.vazovsky.lesson_7_klyueva.presentation.FragmentListener
import app.vazovsky.lesson_7_klyueva.presentation.bridge.BridgeFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class BridgeListFragment : Fragment(R.layout.fragment_bridge_list) {
    companion object {
        const val STATE_PROGRESS_BAR = 0
        const val STATE_RECYCLER_VIEW = 1
        const val STATE_TEXT_ERROR = 2

        fun newInstance(): BridgeListFragment {
            return BridgeListFragment()
        }
    }

    private val recyclerView: RecyclerView
        get() = view?.findViewById(R.id.recyclerView)!! //исправить
    private val viewFlipper: ViewFlipper
        get() = view?.findViewById(R.id.viewFlipper)!!
    private val textViewError: TextView
        get() = view?.findViewById(R.id.textViewError)!!
    private val buttonReloading: MaterialButton
        get() = view?.findViewById(R.id.buttonReloading)!!

    private val viewModel: BridgeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadBridges()
    }

    private var fragmentListener: FragmentListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListener) {
            fragmentListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentListener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_info -> {
                    Toast.makeText(context, "Это классное приложение про развод мостов в Питере", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_map -> {
                    Toast.makeText(context, "Тут могла бы быть карта мостов", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        viewModel.bridgeState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is BrigdeState.Loading -> {
                    setStateLoading()
                }
                is BrigdeState.Data -> {
                    if (state.data.isEmpty()) {
                        setStateEmptyLoaded()
                    } else {
                        setStateSuccessLoaded(state.data)
                    }
                }
                is BrigdeState.Error -> {
                    setStateError(state.error)
                }
                else -> setStateLoading()
            }

        })
    }

    private fun loadBridges() {
        viewModel.loadBridges()
    }

    private fun setStateLoading() {
        viewFlipper.displayedChild = STATE_PROGRESS_BAR
    }

    private fun setStateSuccessLoaded(data: List<Bridge>) {
        viewFlipper.displayedChild = STATE_RECYCLER_VIEW
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = BridgeListAdapter()
        adapter.setItems(data)
        adapter.onItemClick = {
            fragmentListener?.openFragment(BridgeFragment.newInstance(it))
        }
        recyclerView.adapter = adapter
    }

    private fun setStateEmptyLoaded() {
        viewFlipper.displayedChild = STATE_TEXT_ERROR
        textViewError.text = "Пусто"
        textViewError.setTextColor(Color.GRAY)
        buttonReloading.setOnClickListener {
            loadBridges()
        }
    }

    private fun setStateError(e: Exception) {
        viewFlipper.displayedChild = STATE_TEXT_ERROR
        textViewError.text = e.message
        textViewError.setTextColor(Color.RED)
        buttonReloading.setOnClickListener {
            loadBridges()
        }
    }
}