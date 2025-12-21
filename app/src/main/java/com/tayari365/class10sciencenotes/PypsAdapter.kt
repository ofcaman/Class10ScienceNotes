package com.tayari365.class10sciencenotes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PypsAdapter(private val pyps: List<PyqItem>) :
    RecyclerView.Adapter<PypsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numberText: TextView = view.findViewById(R.id.pyqNumber)
        val nameText: TextView = view.findViewById(R.id.pyqName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pyp, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pyp = pyps[position]

        // Set the number and name
        holder.numberText.text = pyp.number.toString()
        holder.nameText.text = pyp.name

        // Set the background color of the circle
        holder.numberText.background.setTint(pyp.backgroundColor)

        // Set click listener to navigate to NotesViewerActivity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, NotesViewerActivity::class.java).apply {
                putExtra("SUBJECT_ID", "pyp")
                putExtra("POSITION", position)
                putExtra("IS_PYP", true)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = pyps.size
}

