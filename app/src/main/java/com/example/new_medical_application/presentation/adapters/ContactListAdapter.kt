package com.example.new_medical_application.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.new_medical_application.R
import com.example.new_medical_application.business.model.EmergencyContact
import com.google.android.material.card.MaterialCardView

class ContactListAdapter(
    private val contactList: List<EmergencyContact>,
    private val onTopicClick: (String) -> Unit
) : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.contact_name_text)
        val phoneText: TextView = itemView.findViewById(R.id.contact_phone_number_text)
        val emailText: TextView = itemView.findViewById(R.id.contact_email_text)
        private val cardView: MaterialCardView = itemView.findViewById(R.id.contact_info_card)

        fun bind(phoneNumber: String) {
            cardView.setOnClickListener {
                onTopicClick(phoneNumber)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun getItemCount() = contactList.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.nameText.text = contact.name
        holder.emailText.text = contact.email
        holder.phoneText.text = contact.phoneNumber
        holder.bind(contact.phoneNumber)
    }
}