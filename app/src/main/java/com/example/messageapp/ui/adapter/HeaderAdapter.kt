package com.example.messageapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.messageapp.databinding.ItemHourAgoBinding
import com.example.messageapp.model.LocalMessage

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.HeaderAdapterViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<LocalMessage>() {
        override fun areItemsTheSame(oldItem: LocalMessage, newItem: LocalMessage): Boolean {
            return oldItem.smsDate == newItem.smsDate
        }

        override fun areContentsTheSame(oldItem: LocalMessage, newItem: LocalMessage): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this, diffCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderAdapterViewHolder {
        val itemHourAgoBinding: ItemHourAgoBinding =
            ItemHourAgoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderAdapterViewHolder(itemHourAgoBinding)
    }
    var allMessage: List<LocalMessage>
        get() = differ.currentList
        set(value) = differ.submitList(value)
    class HeaderAdapterViewHolder(var binding: ItemHourAgoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(localMessage: LocalMessage) {
            binding.tvHour.text= localMessage.diff.toString().plus(" ").plus("Hours ago")
        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: HeaderAdapterViewHolder, position: Int) {
        val currentMessage = allMessage[position]
        holder.bind(currentMessage)

    }
}