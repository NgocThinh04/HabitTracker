package com.yourapp.habittracker.ui.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yourapp.habittracker.R
import com.yourapp.habittracker.databinding.FragmentStatsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StatsFragment : Fragment() {
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    private fun observeData() {
        // Observe all habits for filter chips
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allHabits.collectLatest { habits ->
                setupFilterChips(habits)
            }
        }

        // Observe selected habit
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedHabit.collectLatest { habit ->
                habit?.let {
                    binding.tvHabitName.text = it.name
                    binding.tvHabitDescription.text = it.description
                }
            }
        }

        // Observe weekly progress
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weeklyProgress.collectLatest { progress ->
                binding.weeklyProgressView.setData(progress)
            }
        }
    }

    private fun setupFilterChips(habits: List<com.yourapp.habittracker.data.local.entity.HabitEntity>) {
        val container = binding.llFilterContainer
        container.removeAllViews()

        // Add "All" chip
        val allChip = createChip("All", isSelected = viewModel.selectedHabit.value == null)
        allChip.setOnClickListener {
            viewModel.selectHabit(habits.firstOrNull() ?: return@setOnClickListener)
        }
        container.addView(allChip)

        // Add habit chips
        habits.forEach { habit ->
            val chip = createChip(habit.name, isSelected = viewModel.selectedHabit.value?.id == habit.id)
            chip.setOnClickListener {
                viewModel.selectHabit(habit)
            }
            container.addView(chip)
        }
    }

    private fun createChip(text: String, isSelected: Boolean): TextView {
        return TextView(requireContext()).apply {
            this.text = text
            setPadding(48, 20, 48, 20)
            setTextColor(if (isSelected) Color.WHITE else Color.parseColor("#555555"))
            background = resources.getDrawable(R.drawable.bg_round_stats, null)
            background.setTint(if (isSelected) Color.parseColor("#111111") else Color.WHITE)
            textSize = 14f
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                marginEnd = 24
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}