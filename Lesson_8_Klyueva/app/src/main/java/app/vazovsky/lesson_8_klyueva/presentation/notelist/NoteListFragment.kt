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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNotes(requireContext())
        viewModel.subscribeToNotes(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.onItemClick = { note ->
            fragmentListener?.goToFragment(NoteFragment.newInstance(note))
        }
        adapter.onItemLongClick = { note, action ->
            when (action) {
                CONTEXT_ARCHIVE -> {
                    note.isArchive = !note.isArchive
                    viewModel.insert(requireContext(), note)
                }
                CONTEXT_DELETE -> {
                    viewModel.delete(requireContext(), note)
                }
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.notesLiveData.observe(viewLifecycleOwner) { notes ->
            adapter.setItems(notes)
        }
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    viewFlipper.displayedChild = STATE_PROGRESS_BAR
                }
                is State.Data -> {
                    if (state.count > 0) {
                        viewFlipper.displayedChild = STATE_RECYCLER_VIEW
                    } else {
                        viewFlipper.displayedChild = STATE_TEXT_ERROR
                        binding.textViewError.text = "Пусто"
                        binding.buttonUpdate.visibility = View.GONE
                    }
                }
                is State.Error -> {
                    viewFlipper.displayedChild = STATE_TEXT_ERROR
                    binding.buttonUpdate.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            viewModel.loadNotes(requireContext())
                        }
                    }
                    binding.textViewError.text = state.error.message
                }
            }
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    val searchItem: MenuItem = it as MenuItem
                    (searchItem.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String): Boolean {
                            adapter.filter(query)
                            return false
                        }

                        override fun onQueryTextChange(newText: String): Boolean {
                            adapter.filter(newText)
                            return false
                        }
                    })
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
                title = "",
                content = ""
            )
            fragmentListener?.goToFragment(
                NoteFragment.newInstance(note)
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
//        val searchItem: MenuItem = menu.findItem(R.id.menu_search) as MenuItem
//        (searchItem.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                Toast.makeText(requireContext(), "Поиск", Toast.LENGTH_SHORT).show()
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                Toast.makeText(requireContext(), newText, Toast.LENGTH_SHORT).show()
//                return false
//            }
//        })


//        val sv =
//            sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(s: String): Boolean {
//                    return false
//                }
//
//                override fun onQueryTextChange(s: String): Boolean {
//                    //when the text change
//                    search(s)
//                    return false
//                }
//            })
//        sv.setOnCloseListener { //when canceling the search
//            false
//        }
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