package com.example.utstam.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.utstam.api.ApiConfig
import com.example.utstam.databinding.ActivityLoginBinding
import com.example.utstam.model.User
import com.example.utstam.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Repository.init(this)
        if (Repository.loggedInUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                performLogin(email, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun performLogin(email: String, password: String) {
        val client = ApiConfig.getApiService().getUsers()

        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val apiUsers = response.body() ?: emptyList()
                val allUsers = apiUsers + Repository.users

                val user = allUsers.find { it.email == email && it.password == password }
                if (user != null) {
                    if (user.name == null) user.name = email.substringBefore("@")
                    Repository.login(user, this@LoginActivity)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    val emailExists = allUsers.any { it.email == email }
                    if (emailExists) {
                        Toast.makeText(this@LoginActivity, "Password salah", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@LoginActivity, "Email tidak terdaftar", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                val localUser = Repository.users.find { it.email == email && it.password == password }
                if (localUser != null) {
                    Repository.login(localUser, this@LoginActivity)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Error Koneksi: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}