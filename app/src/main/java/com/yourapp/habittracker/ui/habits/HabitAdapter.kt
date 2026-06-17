package com.yourapp.habittracker.ui.habits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yourapp.habittracker.data.local.entity.HabitEntity
import com.yourapp.habittracker.databinding.ItemHabitGamifiedBinding

class HabitAdapter(
    private val onHabitClick: (HabitEntity) -> Unit,
    private val onCheckClick: (HabitEntity) -> Unit,
    private val onPartialClick: (HabitEntity) -> Unit,
    private val onSkipClick: (HabitEntity) -> Unit
) : ListAdapter<HabitEntity, HabitAdapter.HabitViewHolder>(HabitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitGamifiedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HabitViewHolder(
        private val binding: ItemHabitGamifiedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: HabitEntity) {
            binding.apply {
                tvHabitTitle.text = habit.name
                tvXp.text = "✦ +${habit.xpReward}XP"
                cbHabitCheck.isChecked = false

                // Click vào thẻ để ẩn/hiện menu
                root.setOnClickListener {
                    llActionMenu.visibility =
                        if (llActionMenu.visibility == View.VISIBLE) View.GONE
                        else View.VISIBLE
                }

                // Checkbox
                cbHabitCheck.setOnClickListener {
                    onCheckClick(habit)
                }

                // Nút Partial
                btnPartial.setOnClickListener {
                    onPartialClick(habit)
                    llActionMenu.visibility = View.GONE
                }

                // Nút Skip
                btnSkip.setOnClickListener {
                    onSkipClick(habit)
                    llActionMenu.visibility = View.GONE
                }
            }
        }
    }
}

class HabitDiffCallback : DiffUtil.ItemCallback<HabitEntity>() {
    override fun areItemsTheSame(oldItem: HabitEntity, newItem: HabitEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HabitEntity, newItem: HabitEntity): Boolean {
        return oldItem == newItem
    }
}