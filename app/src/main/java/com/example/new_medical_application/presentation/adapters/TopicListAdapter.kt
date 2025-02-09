package com.example.new_medical_application.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.new_medical_application.R
import com.example.new_medical_application.business.model.MedicalTopic
import com.google.android.material.card.MaterialCardView

class TopicListAdapter(
    private var topicList: List<MedicalTopic>,
    private val onTopicClick: (String) -> Unit
) :
    RecyclerView.Adapter<TopicListAdapter.TopicViewHolder>() {

    inner class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberText: TextView = itemView.findViewById(R.id.number_topic_text)
        val titleText: TextView = itemView.findViewById(R.id.name_topic_text)
        private val cardView: MaterialCardView = itemView.findViewById(R.id.topic_card_view)

        fun bind(id: String) {
            cardView.setOnClickListener {
                onTopicClick(id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        return TopicViewHolder(itemView)
    }

    override fun getItemCount() = topicList.size

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.numberText.text = (position + 1).toString()
        holder.titleText.text = topicList[position].title
        holder.bind(topicList[position].id)
    }
}