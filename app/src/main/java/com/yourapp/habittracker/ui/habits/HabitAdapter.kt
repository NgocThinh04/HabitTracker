package com.yourapp.habittracker.ui.habits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yourapp.habittracker.data.Habit
import com.yourapp.habittracker.databinding.ItemHabitGamifiedBinding

class HabitAdapter(
    private val onHabitClick: (Habit) -> Unit,
    private val onCheckClick: (Habit, Boolean) -> Unit
) : ListAdapter<Habit, HabitAdapter.HabitViewHolder>(HabitDiffCallback()) {

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

        fun bind(habit: Habit) {
            binding.apply {
                tvHabitTitle.text = habit.name
                tvXp.text = "✦ +${habit.xpReward}XP"

                cbHabitCheck.setOnCheckedChangeListener(null)
                cbHabitCheck.isChecked = habit.currentValue >= habit.targetValue
                cbHabitCheck.setOnCheckedChangeListener { _, isChecked ->
                    onCheckClick(habit, isChecked)
                }

                // SỰ KIỆN MỚI: Click vào thẻ để Ẩn/Hiện Menu
                root.setOnClickListener {
                    if (llActionMenu.visibility == View.VISIBLE) {
                        llActionMenu.visibility = View.GONE
                    } else {
                        llActionMenu.visibility = View.VISIBLE
                    }
                }

                // Xử lý khi bấm nút Skip
                btnSkip.setOnClickListener {
                    // Tạm thời thu gọn menu lại
                    llActionMenu.visibility = View.GONE
                }

                // Xử lý khi bấm nút Partial
                btnPartial.setOnClickListener {
                    // Tạm thời thu gọn menu lại
                    llActionMenu.visibility = View.GONE
                }
            }
        }
    }
}

class HabitDiffCallback : DiffUtil.ItemCallback<Habit>() {
    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem == newItem
    }
}