package com.tayari365.class10sciencenotes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val dataList: ArrayList<rvModel>, private val context: Context) :
    RecyclerView.Adapter<Adapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.itemIcon)
        val name: TextView = view.findViewById(R.id.itemName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]

        // Bind data to views
        holder.icon.setImageResource(currentItem.icon)
        holder.name.text = currentItem.name

        // Click listener
        holder.itemView.setOnClickListener {
            when (currentItem.name) {
                "Ten Sets" -> {
                    val intent = Intent(context, TenSetLoaderActivity::class.java)
                    context.startActivity(intent)
                }
                else -> {
                    val intent = Intent(context, ChapterLoadActivity::class.java)
                    intent.putExtra("CHAPTER_NAME", currentItem.name)
                    intent.putExtra("CHAPTER_POSITION", position)
                    context.startActivity(intent)
                }
            }
        }
    }
}

