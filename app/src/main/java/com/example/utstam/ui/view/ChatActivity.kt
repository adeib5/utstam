package com.example.utstam.ui.view

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
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

        // Aktifkan Edge-to-Edge agar layout bisa merespons keyboard dengan halus
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Atur Insets untuk Judul (Status Bar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.tvChatTitle) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = systemBars.top + (16 * resources.displayMetrics.density).toInt()
            }
            insets
        }

        // Atur Insets untuk Input Bar agar melayang di atas Navigation Bar atau Keyboard
        ViewCompat.setOnApplyWindowInsetsListener(binding.layoutInputContainer) { v, insets ->
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            
            val isKeyboardVisible = ime.bottom > 0
            val bottomPadding = if (isKeyboardVisible) ime.bottom else systemBars.bottom
            
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val margin16 = (16 * resources.displayMetrics.density).toInt()
                leftMargin = margin16
                rightMargin = margin16
                // Jika keyboard muncul, kita tempelkan ke keyboard. Jika tidak, beri jarak dari navigasi bawah.
                bottomMargin = if (isKeyboardVisible) {
                    bottomPadding + (8 * resources.displayMetrics.density).toInt()
                } else {
                    bottomPadding + margin16
                }
            }
            insets
        }

        setupRecyclerView()

        binding.btnSend.setOnClickListener {
            sendMessage()
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter(Repository.chatMessages)
        val layoutManager = LinearLayoutManager(this)
        // Dihapus agar bubble chat mulai dari atas jika pesan masih sedikit
        // layoutManager.stackFromEnd = true
        binding.rvChat.layoutManager = layoutManager
        binding.rvChat.adapter = adapter
        
        if (Repository.chatMessages.isNotEmpty()) {
            binding.rvChat.scrollToPosition(Repository.chatMessages.size - 1)
        }
    }

    private fun sendMessage() {
        val message = binding.etMessage.text.toString().trim()
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
            }, 1000)
        }
    }
}
