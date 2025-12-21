package com.tayari365.class10sciencenotes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter class for the RecyclerView
class MoreAdapter(private val chapters: List<Chapter>) :
    RecyclerView.Adapter<MoreAdapter.ChapterViewHolder>() {

    // ViewHolder for chapter items
    class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.chapterImage)
        val titleTextView: TextView = itemView.findViewById(R.id.chapterTitle)
    }

    // Inflates the item layout and creates the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chapter, parent, false)
        return ChapterViewHolder(view)
    }

    // Binds data to the ViewHolder
    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val chapter = chapters[position]
        holder.imageView.setImageResource(chapter.imageResId)
        holder.titleTextView.text = chapter.title

        // Handle item click
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            // Start ChapterLoadActivity and pass the chapter title and position
            val intent = Intent(context, ChapterLoadActivity::class.java).apply {
                putExtra("CHAPTER_NAME", chapter.title)
                putExtra("CHAPTER_POSITION", position)
            }
            context.startActivity(intent)
        }
    }

    // Returns the total number of items in the dataset
    override fun getItemCount() = chapters.size
}
