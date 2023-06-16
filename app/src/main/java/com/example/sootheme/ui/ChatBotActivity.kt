package com.example.sootheme.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sootheme.adapter.ChatAdapter
import com.example.sootheme.data.Messages
import com.example.sootheme.databinding.ActivityChatBotBinding
import com.example.sootheme.model.ChatBotViewModel
import com.example.sootheme.network.ViewModelFactory

class ChatBotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBotBinding
    private lateinit var adapter: ChatAdapter
    private val viewModel: ChatBotViewModel by viewModels {
        ViewModelFactory.getInstance(context = this)
    }
    private val recyclerView: RecyclerView
        get() = binding.messageList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(this)
        recyclerView.adapter = adapter

        binding.btnSend.setOnClickListener {
            val userMsg = binding.txtMessage.text.toString()
            if (userMsg.isNotEmpty()) {
                adapter.addMessage(Messages("user", userMsg))
                binding.txtMessage.text.clear()
                val inputManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
                )
            } else {
                binding.txtMessage.error = "Please enter your message"
            }

            viewModel.chatBot(userMsg)
            viewModel.getBotResponse().observe(this@ChatBotActivity) {
                if (it != null) {
                    adapter.addMessage(Messages("bot", it.bot))
                    viewModel.botResponse.value = null
                } else {
                    Log.e("ChatBotActivity", "Data tidak ditemukan")
                }
            }
        }
    }
}