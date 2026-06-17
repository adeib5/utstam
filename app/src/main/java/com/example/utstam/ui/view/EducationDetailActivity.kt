package com.example.utstam.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.example.utstam.databinding.ActivityEducationDetailBinding
import com.example.utstam.model.Education

class EducationDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEducationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menggunakan IntentCompat membantu IDE mengenali tipe data Education dengan lebih baik
        val education = IntentCompat.getParcelableExtra(intent, "extra_education", Education::class.java)
        
        education?.let {
            binding.tvEduDetailTitle.text = it.title
            binding.tvEduDetailContent.text = it.content

            if (it.imageResId != 0) {
                binding.ivEduDetail.setImageResource(it.imageResId)
            }
        }
    }
}
