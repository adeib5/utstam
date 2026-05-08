package com.example.utstam.ui.report

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.utstam.R
import com.example.utstam.databinding.ActivityReportBinding
import com.example.utstam.model.DataRepository
import com.example.utstam.model.Report
import java.util.*

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupForm()

        binding.btnUploadPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        binding.btnSubmit.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun setupForm() {
        val categories = resources.getStringArray(R.array.bullying_categories)
        val catAdapter = ArrayAdapter(this, R.layout.list_item_dropdown, categories)
        (binding.spinnerCategory as? AutoCompleteTextView)?.setAdapter(catAdapter)

        val locations = resources.getStringArray(R.array.bullying_locations)
        val locAdapter = ArrayAdapter(this, R.layout.list_item_dropdown, locations)
        (binding.spinnerLocation as? AutoCompleteTextView)?.setAdapter(locAdapter)

        binding.spinnerCategory.setOnClickListener { 
            (it as? AutoCompleteTextView)?.showDropDown() 
        }
        binding.spinnerLocation.setOnClickListener { 
            (it as? AutoCompleteTextView)?.showDropDown() 
        }

        val dateListener = View.OnClickListener { showDatePicker() }
        binding.etDate.setOnClickListener(dateListener)
        binding.tilDate.setStartIconOnClickListener(dateListener)
        binding.tilDate.setOnClickListener(dateListener)
        
        binding.etDate.isFocusable = false
        binding.etDate.isClickable = true
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, R.style.CustomDatePickerDialog, { _, y, m, d ->
            selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", d, m + 1, y)
            binding.etDate.setText(selectedDate)
        }, year, month, day)
        
        datePickerDialog.show()
    }

    private fun validateAndSubmit() {
        val category = binding.spinnerCategory.text.toString().trim()
        val location = binding.spinnerLocation.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val dateValue = binding.etDate.text.toString().trim()

        if (category.isEmpty() || location.isEmpty() || dateValue.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "⚠️ Mohon lengkapi: Kategori, Lokasi, Tanggal, dan Cerita", Toast.LENGTH_LONG).show()
            return
        }

        val user = DataRepository.loggedInUser
        val isAnonymous = DataRepository.isAnonymousMode(this)
        val displayName = if (isAnonymous) "Anonim" else (user?.name ?: "Mahasiswa")

        val newReport = Report(
            id = UUID.randomUUID().toString(),
            userId = user?.id ?: "unknown",
            reporterDisplayName = displayName,
            category = category,
            location = location,
            date = dateValue,
            description = description,
            photoUri = null
        )

        DataRepository.reports.add(0, newReport)
        Toast.makeText(this, "✅ Laporan Berhasil Dikirim oleh $displayName", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            binding.ivPreview.visibility = View.VISIBLE
            binding.ivPreview.setImageURI(data?.data)
        }
    }
}
