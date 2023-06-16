package com.example.sootheme.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sootheme.R
import com.example.sootheme.data.Messages

private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2

class ChatAdapter(val context: Context) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(messages: Messages) {}
    }

    private val messages: ArrayList<Messages> = ArrayList()

    fun addMessage(message: Messages) {
        messages.add(message)
        notifyDataSetChanged()
    }

    inner class MyMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView? = view.findViewById(R.id.txtMyMessage)

        override fun bind(message: Messages) {
            messageText?.text = message.message
        }
    }

    inner class OtherMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView? = view.findViewById(R.id.txtOtherMessage)

        override fun bind(message: Messages) {
            messageText?.text = message.message
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]

        return if (message.sender == "user") {
            VIEW_TYPE_MY_MESSAGE
        } else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.my_message, parent, false)
            )
        } else {
            OtherMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.other_message, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages.get(position)

        holder?.bind(message)
    }

}