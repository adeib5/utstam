package com.example.utstam.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.utstam.R
import com.example.utstam.databinding.ActivityReportDetailBinding
import com.example.utstam.model.Report

class ReportDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val report = intent.getParcelableExtra<Report>("extra_report")
        report?.let { setupUI(it) }
    }

    private fun setupUI(report: Report) {
        binding.tvDetailLocation.text = "${report.category} - ${report.location}"
        binding.tvDetailReporter.text = "Dilaporkan oleh: ${report.reporterDisplayName}"
        binding.tvDetailDate.text = report.date
        binding.tvDetailDescription.text = report.description
        binding.tvDetailStatus.text = report.status
        
        val statusBg = if (report.status == "Selesai") R.drawable.bg_status_selesai else R.drawable.bg_status_diproses
        binding.tvDetailStatus.setBackgroundResource(statusBg)

        if (!report.photoUri.isNullOrEmpty()) {
            binding.ivDetailPhoto.visibility = View.VISIBLE
            Glide.with(this)
                .load(report.photoUri)
                .placeholder(R.drawable.banner_kampus)
                .error(R.drawable.illustration_edu_1)
                .into(binding.ivDetailPhoto)
        } else {
            binding.ivDetailPhoto.visibility = View.GONE
        }

        if (report.adminResponse != null) {
            binding.tvAdminResponseLabel.visibility = View.VISIBLE
            binding.tvAdminResponse.visibility = View.VISIBLE
            binding.tvAdminResponse.text = report.adminResponse
        }
    }
}