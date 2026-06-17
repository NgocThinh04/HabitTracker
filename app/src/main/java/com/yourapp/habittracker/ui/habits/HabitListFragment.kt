package com.yourapp.habittracker.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yourapp.habittracker.data.Habit
import com.yourapp.habittracker.data.TimeBlock
import com.yourapp.habittracker.databinding.FragmentHabitListBinding

class HabitListFragment : Fragment() {
    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Khởi tạo Adapter
        val adapter = HabitAdapter(
            onHabitClick = { habit -> /* Tạm thời chưa làm gì khi click */ },
            onCheckClick = { habit, isChecked -> /* Tạm thời chưa làm gì khi check */ }
        )

        // Cài đặt RecyclerView hiển thị danh sách
        binding.rvHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHabits.adapter = adapter

        // Tạo dữ liệu giả (Dummy Data) để xem giao diện lên hình như thế nào
        val dummyHabits = listOf(
            Habit(
                id = 1,
                name = "Wake up at 7:30 AM",
                xpReward = 25,
                timeBlock = TimeBlock.DAY_START,
                targetValue = 1f,
                currentValue = 1f // Đã hoàn thành, checkbox sẽ sáng
            ),
            Habit(
                id = 2,
                name = "Drink 1.8L water",
                xpReward = 25,
                timeBlock = TimeBlock.THE_DAY,
                targetValue = 1.8f,
                currentValue = 0.5f // Chưa hoàn thành
            )
        )

        // Truyền dữ liệu vào Adapter để vẽ lên màn hình
        adapter.submitList(dummyHabits)
        // Xử lý sự kiện bấm vào nút Lửa (🔥 1) để mở Màn hình Thành tích
        binding.tvStreakBadge.setOnClickListener {
            val bottomSheet = StreakBadgesBottomSheet()
            bottomSheet.show(parentFragmentManager, "StreakBadgesBottomSheet")
        }

        binding.fabAddHabit.setOnClickListener {
            val addTaskSheet = AddTaskBottomSheet()
            addTaskSheet.show(parentFragmentManager, "AddTaskBottomSheet")
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
    // Xử lý sự kiện bấm nút "New Task" màu đen
