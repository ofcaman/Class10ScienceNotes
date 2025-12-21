package com.tayari365.class10sciencenotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MindMapAdapter(
    private val mindMaps: List<MindMap>,
    private val onItemClick: (Int, Int) -> Unit
) : RecyclerView.Adapter<MindMapAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pyqNumber: TextView = itemView.findViewById(R.id.pyqNumber)
        val pyqName: TextView = itemView.findViewById(R.id.pyqName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mind_map, parent, false)  // Inflate your CardView layout
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mindMap = mindMaps[position]

        holder.pyqNumber.text = mindMap.id.toString()  // Assuming the ID is the number
        holder.pyqName.text = mindMap.title

        // Set an OnClickListener on the entire card
        holder.itemView.setOnClickListener {
            onItemClick(mindMap.id, position)
        }
    }

    override fun getItemCount() = mindMaps.size
}
