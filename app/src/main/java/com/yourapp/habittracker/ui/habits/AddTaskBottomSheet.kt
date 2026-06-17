package com.yourapp.habittracker.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yourapp.habittracker.databinding.FragmentAddTaskBinding

class AddTaskBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Xử lý sự kiện khi bấm nút "Save Task"
        binding.btnSaveTask.setOnClickListener {
            // Tạm thời chỉ đóng màn hình lại (sau này sẽ lưu vào Database ở Tuần 2)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}