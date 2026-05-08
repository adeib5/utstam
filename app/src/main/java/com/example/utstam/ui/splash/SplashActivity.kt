package com.example.utstam.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.utstam.MainActivity
import com.example.utstam.R
import com.example.utstam.databinding.ActivitySplashBinding
import com.example.utstam.model.DataRepository
import com.example.utstam.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        DataRepository.init(this)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.ivSplashLogo.startAnimation(fadeIn)
        binding.tvSplashName.startAnimation(fadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            if (DataRepository.loggedInUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 3000)
    }
}