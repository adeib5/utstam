package com.example.utstam.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utstam.databinding.ActivityChatBinding
import com.example.utstam.model.ChatMessage
import com.example.utstam.repository.Repository
import com.example.utstam.ui.adapter.ChatAdapter

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatAdapter(Repository.chatMessages)
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        binding.rvChat.adapter = adapter
        binding.rvChat.scrollToPosition(Repository.chatMessages.size - 1)

        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            if (message.isNotEmpty()) {
                val userMsg = ChatMessage(message, true)
                Repository.chatMessages.add(userMsg)
                adapter.notifyItemInserted(Repository.chatMessages.size - 1)
                binding.rvChat.scrollToPosition(Repository.chatMessages.size - 1)
                binding.etMessage.text.clear()

                binding.root.postDelayed({
                    val reply = ChatMessage("Terima kasih atas laporannya. Admin akan meninjau pesan Anda segera.", false)
                    Repository.chatMessages.add(reply)
                    adapter.notifyItemInserted(Repository.chatMessages.size - 1)
                    binding.rvChat.scrollToPosition(Repository.chatMessages.size - 1)
                }, 1500)
            }
        }
    }
}