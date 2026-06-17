package com.yourapp.habittracker.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yourapp.habittracker.R

class FeedsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Gọi file giao diện fragment_feeds.xml
        return inflater.inflate(R.layout.fragment_feeds, container, false)
    }
}