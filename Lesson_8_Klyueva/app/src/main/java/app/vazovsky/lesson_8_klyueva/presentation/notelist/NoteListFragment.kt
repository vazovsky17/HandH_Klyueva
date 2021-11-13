package app.vazovsky.lesson_8_klyueva.presentation.notelist

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.vazovsky.lesson_8_klyueva.R
import app.vazovsky.lesson_8_klyueva.databinding.FragmentNoteListBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class NoteListFragment : Fragment(R.layout.fragment_note_list) {
    companion object {
        fun newInstance(): NoteListFragment {
            return NoteListFragment()
        }
    }

    private val binding by viewBinding(FragmentNoteListBinding::bind)
    private val viewModel: NoteListViewModel by viewModels()
}