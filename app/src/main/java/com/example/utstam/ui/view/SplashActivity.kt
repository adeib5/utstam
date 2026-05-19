package com.example.utstam.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.utstam.R
import com.example.utstam.databinding.ActivitySplashBinding
import com.example.utstam.repository.Repository

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Repository.init(this)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.ivSplashLogo.startAnimation(fadeIn)
        binding.tvSplashName.startAnimation(fadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            if (Repository.loggedInUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 3000)
    }
}