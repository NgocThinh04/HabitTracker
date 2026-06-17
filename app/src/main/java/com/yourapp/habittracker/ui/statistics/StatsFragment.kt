package com.yourapp.habittracker.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yourapp.habittracker.R

class StatsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Gọi file giao diện fragment_stats.xml
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }
}