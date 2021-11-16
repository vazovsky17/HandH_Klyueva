package app.vazovsky.lesson_8_klyueva.presentation.notelist

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_8_klyueva.data.db.entity.NoteEntity

class NoteListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private val items = mutableListOf<NoteEntity>()
    override fun getItemCount() = items.size
    fun setItems(items: List<NoteEntity>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    private val filteredItems = mutableListOf<NoteEntity>()

    lateinit var onItemClick: (NoteEntity) -> Unit
    lateinit var onItemLongClick: (NoteEntity, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteItemViewHolder(parent, onItemClick, onItemLongClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NoteItemViewHolder).bind(items[position])
    }

    private var notesFilter: NotesFilter? = null

    override fun getFilter(): Filter {
        if (notesFilter == null) {
            notesFilter = NotesFilter(this, items)
        }
        return notesFilter as NotesFilter
    }

    class NotesFilter(private val adapter: NoteListAdapter, private val originalItems: MutableList<NoteEntity>) : Filter() {
        private val filteredItems: MutableList<NoteEntity> = mutableListOf()
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            filteredItems.clear()
            val results = FilterResults()
            if (constraint?.length == 0) {
                filteredItems.addAll(originalItems)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()

                for (note in originalItems) {
                    if (note.title.contains(filterPattern)) {
                        filteredItems.add(note)
                    }
                }
            }
            results.values = filteredItems
            results.count = filteredItems.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            adapter.filteredItems.clear()
            adapter.filteredItems.addAll(results!!.values as List<NoteEntity>)
            adapter.notifyDataSetChanged()
        }

    }
}