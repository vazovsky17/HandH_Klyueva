package app.vazovsky.lesson_8_klyueva.presentation.notelist

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_8_klyueva.R
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity
import app.vazovsky.lesson_8_klyueva.databinding.NoteItemBinding
import app.vazovsky.lesson_8_klyueva.presentation.notelist.NoteListFragment.Companion.CONTEXT_ARCHIVE
import app.vazovsky.lesson_8_klyueva.presentation.notelist.NoteListFragment.Companion.CONTEXT_DELETE
import by.kirich1409.viewbindingdelegate.viewBinding

class NoteItemViewHolder(
    val parent: ViewGroup,
    private val onItemClick: (NoteEntity) -> Unit,
    private val onItemLongClick: (NoteEntity, Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
) {
    private val binding by viewBinding(NoteItemBinding::bind)
    fun bind(note: NoteEntity) {
        itemView.setOnClickListener {
            onItemClick(note)
        }

        itemView.setOnLongClickListener {
            val alertDialog: AlertDialog = itemView.let {
                val builder = AlertDialog.Builder(parent.context)
                builder.apply {
                    setPositiveButton(
                        R.string.alert_archive_title
                    ) { _, _ ->
                        onItemLongClick(note, CONTEXT_ARCHIVE)
                    }
                    setNeutralButton(
                        R.string.alert_delete_title
                    ) { _, _ ->
                        onItemLongClick(note, CONTEXT_DELETE)
                    }
                    setNegativeButton(R.string.alert_cancel_title, DialogInterface.OnClickListener { dialog, id ->

                    })
                }
                builder.create()
            }
            alertDialog.show()
            true
        }
        binding.textViewNoteTitle.text = note.title
        binding.textViewNoteContent.text = note.content
        binding.cardViewNoteItem.setCardBackgroundColor(note.color)
        binding.textViewNoteTitle.setTextColor(if (note.color == Color.WHITE) Color.BLACK else Color.WHITE)
        binding.textViewNoteContent.setTextColor(if (note.color == Color.WHITE) Color.GRAY else Color.WHITE)
    }

}