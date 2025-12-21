package com.tayari365.class10sciencenotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tayari365.class10sciencenotes.databinding.ItemNotesssBinding

class NotesLoaderAdapter(
    private val items: List<NotesLoaderItem>,
    private val onNoteClickListener: OnNoteClickListener
) : RecyclerView.Adapter<NotesLoaderAdapter.ViewHolder>() {

    interface OnNoteClickListener {
        fun onNoteClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotesssBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemNotesssBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onNoteClickListener.onNoteClicked(adapterPosition)
            }
        }

        fun bind(item: NotesLoaderItem) {
            binding.pyqNumber.text = item.id.toString()
            binding.pyqName.text = item.title
        }
    }

    // Add this method to get the list of items
    fun getItems(): List<NotesLoaderItem> = items
}

