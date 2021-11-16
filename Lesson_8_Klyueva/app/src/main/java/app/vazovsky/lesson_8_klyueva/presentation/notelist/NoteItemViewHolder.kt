package app.vazovsky.lesson_8_klyueva.presentation.notelist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_8_klyueva.R
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity
import app.vazovsky.lesson_8_klyueva.databinding.NoteItemBinding
import app.vazovsky.lesson_8_klyueva.presentation.notelist.NoteListFragment.Companion.CONTEXT_ARCHIVE
import app.vazovsky.lesson_8_klyueva.presentation.notelist.NoteListFragment.Companion.CONTEXT_DELETE
import by.kirich1409.viewbindingdelegate.viewBinding

class NoteItemViewHolder(
    parent: ViewGroup,
    private val onItemClick: (NoteEntity) -> Unit,
    private val onItemLongClick: (NoteEntity, Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
) {
    private val binding by viewBinding(NoteItemBinding::bind)
    fun bind(note: NoteEntity) {
        itemView.setOnClickListener {
            onItemClick(note)
        }
        itemView.setOnLongClickListener {
            itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
                val archive = menu.add(Menu.NONE, CONTEXT_ARCHIVE, Menu.NONE, "Архивировать")
                val delete = menu.add(Menu.NONE, CONTEXT_DELETE, Menu.NONE, "Удалить")
                archive.setOnMenuItemClickListener {
                    onItemLongClick(note, CONTEXT_ARCHIVE)
                    true
                }
                delete.setOnMenuItemClickListener {
                    onItemLongClick(note, CONTEXT_DELETE)
                    true
                }
            }
            true
        }
        binding.textViewNoteTitle.text = note.title
        binding.textViewNoteContent.text = note.content
        binding.cardViewNoteItem.setCardBackgroundColor(note.color)
        binding.textViewNoteTitle.setTextColor(if (note.color == Color.WHITE) Color.BLACK else Color.WHITE)
        binding.textViewNoteContent.setTextColor(if (note.color == Color.WHITE) Color.GRAY else Color.WHITE)
    }

}