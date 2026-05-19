package com.example.utstam.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utstam.databinding.ItemEducationBinding
import com.example.utstam.model.Education

class EducationAdapter(
    private val list: List<Education>,
    private val onItemClick: (Education) -> Unit
) : RecyclerView.Adapter<EducationAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemEducationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEducationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvItemEduTitle.text = item.title
        holder.binding.tvItemEduDesc.text = item.description

        if (item.imageResId != 0) {
            holder.binding.ivItemEduBanner.setImageResource(item.imageResId)
        }

        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = list.size
}