package app.vazovsky.lesson_8_klyueva.presentation.note

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import app.vazovsky.lesson_8_klyueva.R
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity
import app.vazovsky.lesson_8_klyueva.databinding.FragmentNoteBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class NoteFragment : Fragment(R.layout.fragment_note) {

    private val binding by viewBinding(FragmentNoteBinding::bind)

    companion object {
        const val EXTRA_NOTE = "extra_note"

        fun newInstance(note: NoteEntity): NoteFragment {
            return NoteFragment().apply {
                arguments = bundleOf(
                    EXTRA_NOTE to note
                )
            }
        }
    }
}