package com.tayari365.class10sciencenotes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChapterAdapter(private val chapters: List<Chapter>) :
    RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() { // Fix here: Use ChapterAdapter.ChapterViewHolder

    // ViewHolder for chapter items
    class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.chapterImage)
        val titleTextView: TextView = itemView.findViewById(R.id.chapterTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chapter, parent, false)
        return ChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val chapter = chapters[position]
        holder.imageView.setImageResource(chapter.imageResId)
        holder.titleTextView.text = chapter.title

        // Set click listener for each item
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ChapterLoadActivity::class.java).apply {
                putExtra("CHAPTER_NAME", chapter.title)
                putExtra("CHAPTER_POSITION", position)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chapters.size
    }
}
