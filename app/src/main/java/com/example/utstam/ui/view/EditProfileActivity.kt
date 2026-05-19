package com.example.utstam.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.utstam.databinding.ActivityEditProfileBinding
import com.example.utstam.repository.Repository

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Repository.loggedInUser
        binding.etEditName.setText(user?.name)
        binding.etEditEmail.setText(user?.email)

        binding.btnSaveProfile.setOnClickListener {
            val newName = binding.etEditName.text.toString()
            if (newName.isNotEmpty()) {
                Repository.updateUser(this, newName)
                Toast.makeText(this, "Profil diperbarui", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}