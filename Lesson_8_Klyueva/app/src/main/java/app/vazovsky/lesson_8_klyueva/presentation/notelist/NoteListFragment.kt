package app.vazovsky.lesson_8_klyueva.presentation.notelist

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import app.vazovsky.lesson_8_klyueva.R
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity
import app.vazovsky.lesson_8_klyueva.data.model.State
import app.vazovsky.lesson_8_klyueva.databinding.FragmentNoteListBinding
import app.vazovsky.lesson_8_klyueva.presentation.FragmentListener
import app.vazovsky.lesson_8_klyueva.presentation.note.NoteFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class NoteListFragment : Fragment(R.layout.fragment_note_list) {

    private val binding by viewBinding(FragmentNoteListBinding::bind)
    private val viewModel: NoteListViewModel by viewModels()
    private var fragmentListener: FragmentListener? = null

    companion object {
        const val STATE_PROGRESS_BAR = 0
        const val STATE_RECYCLER_VIEW = 1
        const val STATE_TEXT_ERROR = 2

        fun newInstance(): NoteListFragment {
            return NoteListFragment()
        }
    }

    private val toolbar get() = binding.toolbar
    private val recyclerView get() = binding.recyclerView
    private val fab get() = binding.fab
    private val viewFlipper get() = binding.viewFlipper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.subscribeToState(requireContext())
        viewModel.subscribeToNotes(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteListAdapter()
        adapter.onItemClick = {
            Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    viewFlipper.displayedChild = STATE_PROGRESS_BAR
                }
                is State.Data -> {
                    viewFlipper.displayedChild = STATE_RECYCLER_VIEW
                }
                is State.Error -> {
                    viewFlipper.displayedChild = STATE_TEXT_ERROR
                    binding.textViewError.text = state.error.message
                }
            }
        }
        viewModel.notesLiveData.observe(viewLifecycleOwner) { notes ->
            adapter.setItems(notes)
        }


        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    Toast.makeText(requireContext(), "Поиск", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_archive -> {
                    Toast.makeText(requireContext(), "Архив", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        fab.setOnClickListener {
            val note = NoteEntity(
                title = "д",
                content = "д"
            )
            viewModel.insert(requireContext(), note)
            fragmentListener?.goToFragment(
                NoteFragment.newInstance(note)
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListener) {
            fragmentListener = context
        }
    }

    override fun onDetach() {
        fragmentListener = null
        super.onDetach()
    }
}