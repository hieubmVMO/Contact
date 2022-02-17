package com.hieubm.contact.ui.main.contacts

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hieubm.contact.R
import com.hieubm.contact.databinding.ItemContactBinding
import com.hieubm.contact.ui.main.contacts.ContactAdapter.ItemContactViewHolder
import com.hieubm.contact.util.CenterImageSpan
import com.hieubm.core.data.model.Contact
import javax.inject.Inject

class ContactAdapter @Inject constructor() : ListAdapter<Contact, ItemContactViewHolder>(
    ContactItemCallback()
) {
    var listener: ContactAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        return ItemContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bindData(contact)
    }

    inner class ItemContactViewHolder(
        private val binding: ItemContactBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.apply {
                root.setOnClickListener(this@ItemContactViewHolder)
                btnFavourite.setOnClickListener {
                    val contact = getItem(adapterPosition)
                    listener?.onFavouriteClick(contact)
                }
            }
        }

        fun bindData(contact: Contact) {
            binding.contact = contact
            val name = SpannableStringBuilder(contact.name).apply {
                if (!contact.isFavourite) {
                    append(getSpannableString(R.drawable.ic_star_radius_yellow))
                }
            }
            binding.tvName.setText(name, TextView.BufferType.SPANNABLE)
        }

        private fun getSpannableString(iconResId: Int): SpannableString {
            val spannableString = SpannableString(" @")
            val iconPhoto = ContextCompat.getDrawable(binding.root.context, iconResId)
            iconPhoto!!.setBounds(0, 0, iconPhoto.intrinsicWidth, iconPhoto.intrinsicHeight)
            val position = spannableString.toString().indexOf("@")
            spannableString.setSpan(
                CenterImageSpan(iconPhoto),
                position,
                position + 1,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
            return spannableString
        }

        override fun onClick(view: View) {
            val contact = getItem(adapterPosition)
            listener?.onItemClick(contact)
        }
    }

    interface ContactAdapterListener {

        fun onItemClick(contact: Contact)

        fun onFavouriteClick(contact: Contact)
    }
}

class ContactItemCallback : DiffUtil.ItemCallback<Contact>() {

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}