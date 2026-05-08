package com.example.utstam.ui.education

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utstam.databinding.ActivityEducationBinding
import com.example.utstam.model.DataRepository

class EducationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEducationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = EducationAdapter(DataRepository.educationList) { education ->
            val intent = Intent(this, EducationDetailActivity::class.java)
            intent.putExtra("extra_education", education)
            startActivity(intent)
        }

        binding.rvEducation.layoutManager = LinearLayoutManager(this)
        binding.rvEducation.adapter = adapter
    }
}