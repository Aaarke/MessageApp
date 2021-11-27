package com.example.messageapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.messageapp.databinding.ItemLocalMessageBinding
import com.example.messageapp.model.LocalMessage


class AllMessageAdapter (private var onItemClickedListeners: (LocalMessage) -> Unit):
    RecyclerView.Adapter<AllMessageAdapter.MessageViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<LocalMessage>() {
        override fun areItemsTheSame(oldItem: LocalMessage, newItem: LocalMessage): Boolean {
            return oldItem.smsDate == newItem.smsDate
        }

        override fun areContentsTheSame(oldItem: LocalMessage, newItem: LocalMessage): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var allMessage: List<LocalMessage>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    class MessageViewHolder(var binding: ItemLocalMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(localMessage: LocalMessage) {
            binding.message = localMessage

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemLocalMessageBinding: ItemLocalMessageBinding =
            ItemLocalMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(itemLocalMessageBinding)

    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = allMessage[position]
        holder.bind(currentMessage)
        holder.binding.root.setOnClickListener {
            onItemClickedListeners.invoke(currentMessage)
        }
        holder.binding.executePendingBindings()
    }



    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}