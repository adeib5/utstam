package com.example.utstam.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.utstam.databinding.ActivityRegisterBinding
import com.example.utstam.model.DataRepository
import com.example.utstam.model.User
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterSubmit.setOnClickListener {
            val name = binding.etRegisterName.text.toString().trim()
            val email = binding.etRegisterEmail.text.toString().trim()
            val password = binding.etRegisterPassword.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val newUser = User(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    email = email,
                    password = password
                )
                DataRepository.addUser(this, newUser)
                Toast.makeText(this, "Pendaftaran berhasil, silakan login", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvBackToLogin.setOnClickListener {
            finish()
        }
    }
}