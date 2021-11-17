package app.vazovsky.lesson_8_klyueva.presentation.notelist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity

class NoteListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<NoteEntity>()
    override fun getItemCount() = items.size
    fun setItems(items: List<NoteEntity>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    private val copyItems = mutableSetOf<NoteEntity>()

    lateinit var onItemClick: (NoteEntity) -> Unit
    lateinit var onItemLongClick: (NoteEntity, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteItemViewHolder(parent, onItemClick, onItemLongClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NoteItemViewHolder).bind(items[position])
    }

    fun filter(queryText: String?) {
        copyItems.addAll(items)
        items.clear()
        if (queryText.isNullOrEmpty()) {
            items.addAll(copyItems)
        } else {
            for (note in copyItems) {
                if (note.title.lowercase().contains(queryText.lowercase())) {
                    items.add(note)
                }
            }
        }

        notifyDataSetChanged()
    }
}