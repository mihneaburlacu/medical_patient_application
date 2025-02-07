package com.example.new_medical_application.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.new_medical_application.R
import com.example.new_medical_application.business.model.MedicalTopic

class TopicListAdapter(private val topicList: List<MedicalTopic>) :
    RecyclerView.Adapter<TopicListAdapter.TopicViewHolder>() {

    class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberText: TextView = itemView.findViewById(R.id.number_topic_text)
        val titleText: TextView = itemView.findViewById(R.id.name_topic_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        return TopicViewHolder(itemView)
    }

    override fun getItemCount() = topicList.size

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.numberText.text = position.toString()
        holder.titleText.text = topicList[position].title
    }
}