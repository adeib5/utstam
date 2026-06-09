package com.example.utstam.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        
        // Title: Category (Reporter)
        holder.binding.tvItemLocation.text = "${report.category} (${report.reporterDisplayName})"
        
        // Date formatting
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        holder.binding.tvItemDate.text = sdf.format(Date(report.timestamp))

        // Status styling
        holder.binding.tvItemStatus.text = report.status
        val statusBg = if (report.status == "Selesai") R.drawable.bg_status_selesai else R.drawable.bg_status_diproses
        holder.binding.tvItemStatus.setBackgroundResource(statusBg)

        // Load Thumbnail Image
        if (!report.photoUri.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(report.photoUri)
                .placeholder(R.drawable.ic_report)
                .error(R.drawable.ic_report)
                .into(holder.binding.ivItemPhoto)
        } else {
            holder.binding.ivItemPhoto.setImageResource(R.drawable.ic_report)
        }

        holder.itemView.setOnClickListener { onItemClick(report) }
    }

    override fun getItemCount(): Int = reports.size
}