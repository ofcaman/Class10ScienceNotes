package com.tayari365.class10sciencenotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GridItemAdapter(
    private val itemList: ArrayList<GridItemModel>,
    private val context: Context,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<GridItemAdapter.GridItemViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class GridItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.title)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_grid, parent, false)
        return GridItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.title
        holder.image.setImageResource(item.imageResId)
    }

    override fun getItemCount(): Int = itemList.size
}


