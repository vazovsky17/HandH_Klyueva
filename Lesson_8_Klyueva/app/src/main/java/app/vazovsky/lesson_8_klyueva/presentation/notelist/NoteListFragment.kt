package app.vazovsky.lesson_8_klyueva.presentation.notelist

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity
import app.vazovsky.lesson_8_klyueva.data.model.State
import app.vazovsky.lesson_8_klyueva.databinding.FragmentNoteListBinding
import app.vazovsky.lesson_8_klyueva.presentation.FragmentListener
import app.vazovsky.lesson_8_klyueva.presentation.note.NoteFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import android.widget.SearchView
import app.vazovsky.lesson_8_klyueva.R
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class NoteListFragment : Fragment(R.layout.fragment_note_list) {

    private val binding by viewBinding(FragmentNoteListBinding::bind)
    private val viewModel: NoteListViewModel by viewModels()
    private var fragmentListener: FragmentListener? = null

    companion object {
        const val STATE_PROGRESS_BAR = 0
        const val STATE_RECYCLER_VIEW = 1
        const val STATE_TEXT_ERROR = 2

        const val CONTEXT_ARCHIVE = 101
        const val CONTEXT_DELETE = 102

        fun newInstance(): NoteListFragment {
            return NoteListFragment()
        }
    }

    private val toolbar get() = binding.toolbar
    private val recyclerView get() = binding.recyclerView
    private val fab get() = binding.fab
    private val viewFlipper get() = binding.viewFlipper
    private val adapter = NoteListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListener) {
            fragmentListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNotes(requireContext())
        viewModel.subscribeToNotes(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()

        viewModel.notesLiveData.observe(viewLifecycleOwner) { notes ->
            adapter.setItems(notes)
        }
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> setStateLoading()
                is State.Data -> setStateData(state)
                is State.Error -> setStateError(state.error)
            }
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    searchNotes(it.actionView as SearchView)
                    true
                }
                R.id.menu_archive -> {
                    Toast.makeText(requireContext(), "Архив", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        fab.setOnClickListener { createNewNote() }
    }

    override fun onDetach() {
        fragmentListener = null
        super.onDetach()
    }

    private fun configureRecyclerView() {
        adapter.onItemClick = { note ->
            fragmentListener?.goToFragment(NoteFragment.newInstance(note))
        }
        adapter.onItemLongClick = { note, action ->
            when (action) {
                CONTEXT_ARCHIVE -> archiveNote(note)
                CONTEXT_DELETE -> deleteNote(note)
            }
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

    }

    private fun setStateLoading() {
        viewFlipper.displayedChild = STATE_PROGRESS_BAR
    }

    private fun setStateData(state: State.Data) {
        if (state.count > 0) {
            viewFlipper.displayedChild = STATE_RECYCLER_VIEW
        } else {
            viewFlipper.displayedChild = STATE_TEXT_ERROR
            binding.textViewError.text = "Пусто"
            binding.buttonUpdate.visibility = View.GONE
        }
    }

    private fun setStateError(error: Exception) {
        viewFlipper.displayedChild = STATE_TEXT_ERROR
        binding.buttonUpdate.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                viewModel.loadNotes(requireContext())
            }
        }
        binding.textViewError.text = error.message
    }

    private fun searchNotes(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter(newText)
                return false
            }
        })
    }

    private fun archiveNote(note: NoteEntity) {
        note.isArchive = !note.isArchive
        viewModel.insert(requireContext(), note)
    }

    private fun deleteNote(note: NoteEntity) {
        viewModel.delete(requireContext(), note)
    }

    private fun createNewNote() {
        val note = NoteEntity(title = "", content = "")
        fragmentListener?.goToFragment(NoteFragment.newInstance(note))
    }
}