package com.tayari365.class10sciencenotes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// New Adapter for handling clicks in MoreContentActivity
class MoreContentAdapter(private val chapters: List<Chapter>, private val context: Context) :
    RecyclerView.Adapter<MoreContentAdapter.ChapterViewHolder>() {

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
            when (position) {
                0 -> startActivity(TenSetLoaderActivity::class.java)
                1 -> {
                    val intent = Intent(context, NotesViewerActivity::class.java).apply {
                        putExtra("SUBJECT_ID", "formulas")
                        putExtra("POSITION", position)
                    }
                    context.startActivity(intent)
                }
                2 -> startActivity(MindMapActivity::class.java)
                3 -> startActivity(ImpQuestionLoaderActivity::class.java)
                4 -> startActivity(PypActivity::class.java)
                5 -> startActivity(PyqsActivity::class.java)
                6 -> startActivity(MockTestLoaderActivity::class.java)
                7 -> startActivity(ChatActivity::class.java)
                else -> {
                    // Handle default or additional cases if necessary
                }
            }
        }
    }

    // Returns the total number of items in the dataset
    override fun getItemCount() = chapters.size

    // Helper function to start activities
    private fun startActivity(activityClass: Class<*>) {
        val intent = Intent(context, activityClass)
        context.startActivity(intent)
    }
}
