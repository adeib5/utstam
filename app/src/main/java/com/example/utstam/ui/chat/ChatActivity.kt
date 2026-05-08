package com.example.utstam.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utstam.databinding.ActivityChatBinding
import com.example.utstam.model.ChatMessage
import com.example.utstam.model.DataRepository

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatAdapter(DataRepository.chatMessages)
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        binding.rvChat.adapter = adapter
        binding.rvChat.scrollToPosition(DataRepository.chatMessages.size - 1)

        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            if (message.isNotEmpty()) {
                val userMsg = ChatMessage(message, true)
                DataRepository.chatMessages.add(userMsg)
                adapter.notifyItemInserted(DataRepository.chatMessages.size - 1)
                binding.rvChat.scrollToPosition(DataRepository.chatMessages.size - 1)
                binding.etMessage.text.clear()

                binding.root.postDelayed({
                    val reply = ChatMessage("Terima kasih atas laporannya. Admin akan meninjau pesan Anda segera.", false)
                    DataRepository.chatMessages.add(reply)
                    adapter.notifyItemInserted(DataRepository.chatMessages.size - 1)
                    binding.rvChat.scrollToPosition(DataRepository.chatMessages.size - 1)
                }, 1500)
            }
        }
    }
}