package com.example.utstam.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utstam.R
import com.example.utstam.databinding.ItemHistoryBinding
import com.example.utstam.model.Report
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(
    private val reports: List<Report>,
    private val onItemClick: (Report) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val report = reports[position]
        holder.binding.tvItemLocation.text = "${report.category} - ${report.location}"
        
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        holder.binding.tvItemDate.text = sdf.format(Date(report.timestamp))

        holder.binding.tvItemStatus.text = report.status
        
        val statusBg = if (report.status == "Selesai") R.drawable.bg_status_selesai else R.drawable.bg_status_diproses
        holder.binding.tvItemStatus.setBackgroundResource(statusBg)

        holder.itemView.setOnClickListener { onItemClick(report) }
    }

    override fun getItemCount(): Int = reports.size
}