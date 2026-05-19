package com.example.utstam.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utstam.databinding.ActivityEducationBinding
import com.example.utstam.repository.Repository
import com.example.utstam.ui.adapter.EducationAdapter

class EducationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEducationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = EducationAdapter(Repository.educationList) { education ->
            val intent = Intent(this, EducationDetailActivity::class.java)
            intent.putExtra("extra_education", education)
            startActivity(intent)
        }

        binding.rvEducation.layoutManager = LinearLayoutManager(this)
        binding.rvEducation.adapter = adapter
    }
}