package com.yourapp.habittracker.ui.journey

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yourapp.habittracker.data.local.entity.DaySummaryEntity
import com.yourapp.habittracker.databinding.ItemJourneyDayBinding

class JourneyAdapter(
    private val onFeelingClick: (DaySummaryEntity) -> Unit,
    private val onMediaClick: (DaySummaryEntity) -> Unit,
    private val onJournalClick: (DaySummaryEntity) -> Unit
) : ListAdapter<DaySummaryEntity, JourneyAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJourneyDayBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemJourneyDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(summary: DaySummaryEntity) {
            binding.apply {
                tvDate.text = summary.date
                tvDayTitle.text = summary.title

                // Feeling
                if (summary.feeling != null) {
                    tvFeelingEmoji.text = summary.feeling
                } else {
                    tvFeelingEmoji.text = "😄 😐 😢"
                }
                cvFeeling.setOnClickListener { onFeelingClick(summary) }

                // Tasks
                val completed = summary.completedTasks
                val total = summary.totalTasks
                tvTaskEmojis.text = if (total > 0) "✅ $completed/$total" else "No tasks"

                // Media
                if (summary.mediaUrls != null) {
                    tvMediaHint.text = "View Media"
                } else {
                    tvMediaHint.text = "Add Media"
                }
                cvMedia.setOnClickListener { onMediaClick(summary) }

                // Journal
                if (summary.journalEntry != null) {
                    tvJournalHint.text = summary.journalEntry
                } else {
                    tvJournalHint.text = "Add Text"
                }
                cvJournal.setOnClickListener { onJournalClick(summary) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DaySummaryEntity>() {
        override fun areItemsTheSame(oldItem: DaySummaryEntity, newItem: DaySummaryEntity): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: DaySummaryEntity, newItem: DaySummaryEntity): Boolean {
            return oldItem == newItem
        }
    }
}