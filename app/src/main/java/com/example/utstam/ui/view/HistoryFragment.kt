package com.example.utstam.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utstam.databinding.FragmentHistoryBinding
import com.example.utstam.repository.Repository
import com.example.utstam.ui.adapter.HistoryAdapter

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadData()
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(Repository.reports) { report ->
            val intent = Intent(requireContext(), ReportDetailActivity::class.java)
            intent.putExtra("extra_report", report)
            startActivity(intent)
        }
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter
    }

    private fun loadData() {
        binding.progressBar.visibility = View.VISIBLE
        Repository.fetchReportsFromApi { apiReports ->
            if (isAdded) {
                binding.progressBar.visibility = View.GONE
                if (apiReports != null) {
                    val existingIds = Repository.reports.map { it.id }.toSet()
                    val newReports = apiReports.filter { it.id !in existingIds }
                    Repository.reports.addAll(0, newReports)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat data dari server, menampilkan data lokal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}