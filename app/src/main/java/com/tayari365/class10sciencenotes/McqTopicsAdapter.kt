package com.tayari365.class10sciencenotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class McqTopicsAdapter(
    private val topics: List<McqTopic>,
    private val onItemClick: (Int, Int) -> Unit
) : RecyclerView.Adapter<McqTopicsAdapter.TopicViewHolder>() {

    inner class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val numberText: TextView = itemView.findViewById(R.id.tvNumber)
        private val titleText: TextView = itemView.findViewById(R.id.tvTitle)

        fun bind(topic: McqTopic, position: Int) {
            numberText.text = topic.id.toString()
            titleText.text = topic.title

            itemView.setOnClickListener {
                onItemClick(topic.id, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mcq_topic, parent, false)
        return TopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(topics[position], position)
    }

    override fun getItemCount() = topics.size
}

