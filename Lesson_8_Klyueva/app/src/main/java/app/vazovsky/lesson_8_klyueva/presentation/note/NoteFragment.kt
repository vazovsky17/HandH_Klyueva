package app.vazovsky.lesson_8_klyueva.presentation.note

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.vazovsky.lesson_8_klyueva.R
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity
import app.vazovsky.lesson_8_klyueva.databinding.FragmentNoteBinding
import app.vazovsky.lesson_8_klyueva.presentation.FragmentListener
import by.kirich1409.viewbindingdelegate.viewBinding

class NoteFragment : Fragment(R.layout.fragment_note) {

    private val binding by viewBinding(FragmentNoteBinding::bind)
    private val viewModel: NoteViewModel by viewModels()
    private var fragmentListener: FragmentListener? = null

    companion object {
        private const val EXTRA_NOTE = "extra_note"

        fun newInstance(note: NoteEntity): NoteFragment {
            return NoteFragment().apply {
                arguments = bundleOf(
                    EXTRA_NOTE to note
                )
            }
        }
    }

    private val toolbar get() = binding.toolbar
    private val editTextNoteTitle get() = binding.editTextNoteTitle
    private val editTextNoteContent get() = binding.editTextNoteContent
    private val noteBackground get() = binding.noteBackground

    private val note by lazy {
        arguments?.getParcelable<NoteEntity>(EXTRA_NOTE)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureNote()
        toolbarClick()
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

    private fun configureNote() {
        editTextNoteTitle.apply {
            setText(note.title)
            setTextColor(if (note.color != Color.WHITE) Color.WHITE else Color.BLACK)
        }
        editTextNoteContent.apply {
            setText(note.content)
            setTextColor(if (note.color != Color.WHITE) Color.WHITE else Color.GRAY)
        }
        noteBackground.setBackgroundColor(note.color)
    }

    private fun toolbarClick() {
        toolbar.setNavigationOnClickListener {
            note.title = editTextNoteTitle.text.toString()
            note.content = editTextNoteContent.text.toString()
            viewModel.insert(requireContext(), note)
            fragmentListener?.goBack()
        }
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_color -> {
                    note.color = Color.BLUE
                    noteBackground.setBackgroundColor(note.color)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}