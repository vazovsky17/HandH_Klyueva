package app.vazovsky.lesson_8_klyueva.presentation.notelist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity

class NoteListAdapter() : RecyclerView.Adapter<NoteItemViewHolder>() {

    private val items = mutableListOf<NoteEntity>()
    private val copyItems = mutableSetOf<NoteEntity>()

    lateinit var onItemClick: (NoteEntity) -> Unit
    lateinit var onItemLongClick: (NoteEntity, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        return NoteItemViewHolder(parent, onItemClick, onItemLongClick)
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<NoteEntity>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    fun filter(queryText: String?) {
        copyItems.addAll(items)
        items.clear()
        if (queryText.isNullOrEmpty()) {
            items.addAll(copyItems)
        } else {
            for (note in copyItems) {
                if (note.title.contains(queryText, true) or note.content.contains(queryText, true)) {
                    items.add(note)
                }
            }
        }

        notifyDataSetChanged()
    }
}