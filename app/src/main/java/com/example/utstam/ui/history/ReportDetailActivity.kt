package com.example.utstam.ui.history

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.utstam.R
import com.example.utstam.databinding.ActivityReportDetailBinding
import com.example.utstam.model.Report

class ReportDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val report = intent.getParcelableExtra<Report>("REPORT_DATA")

        report?.let {
            binding.tvDetailLocation.text = it.location
            binding.tvDetailDate.text = it.date
            binding.tvDetailStatus.text = it.status
            binding.tvDetailDescription.text = it.description

            if (it.adminResponse != null) {
                binding.tvAdminResponseLabel.visibility = View.VISIBLE
                binding.tvAdminResponse.visibility = View.VISIBLE
                binding.tvAdminResponse.text = it.adminResponse
            }
            
            val statusBg = if (it.status == "Selesai") R.drawable.bg_status_selesai else R.drawable.bg_status_diproses
            binding.tvDetailStatus.setBackgroundResource(statusBg)
        }
    }
}