package com.example.new_medical_application.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.new_medical_application.R
import com.example.new_medical_application.business.model.Message

class ChatAdapter(
    private var messages: List<Message>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.text_message_me)
        val timeText: TextView = itemView.findViewById(R.id.text_date_me)
        val timestampText: TextView = itemView.findViewById(R.id.text_timestamp_me)
    }

    inner class OtherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.text_message_other)
        val timeText: TextView = itemView.findViewById(R.id.text_date_other)
        val timestampText: TextView = itemView.findViewById(R.id.text_timestamp_other)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].fromMe) VIEW_TYPE_ME else VIEW_TYPE_OTHER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutId = if (viewType == VIEW_TYPE_ME) {
            R.layout.item_chat_me
        } else {
            R.layout.item_chat_other
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return if (viewType == VIEW_TYPE_ME) MeViewHolder(view) else OtherViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is MeViewHolder) {
            holder.timeText.visibility = if (position == 0) View.VISIBLE else View.INVISIBLE
            holder.messageText.text = message.text
            holder.timeText.text = message.date
            holder.timestampText.text = message.timestamp
        } else if (holder is OtherViewHolder) {
            holder.timeText.visibility = View.INVISIBLE
            holder.messageText.text = message.text
            holder.timeText.text = message.date
            holder.timestampText.text = message.timestamp
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMessages(newMessages: List<Message>) {
        messages = newMessages
        notifyDataSetChanged()
    }

    private companion object {
        const val VIEW_TYPE_ME = 0
        const val VIEW_TYPE_OTHER = 1
    }
}