package com.tayari365.class10sciencenotes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PyqsAdapter(private val pyqs: List<PyqItem>) :
    RecyclerView.Adapter<PyqsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numberText: TextView = view.findViewById(R.id.pyqNumber)
        val nameText: TextView = view.findViewById(R.id.pyqName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pyq, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pyq = pyqs[position]

        // Set the number and name
        holder.numberText.text = pyq.number.toString()
        holder.nameText.text = pyq.name

        // Set the background color of the circle
        holder.numberText.background.setTint(pyq.backgroundColor)

        // Set click listener
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, NotesViewerActivity::class.java).apply {
                putExtra("SUBJECT_ID", "model_set")
                putExtra("POSITION", position)
                putExtra("IS_MODEL_SET", true)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = pyqs.size
}

