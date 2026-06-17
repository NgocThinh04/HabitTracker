package com.yourapp.habittracker.ui.journey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yourapp.habittracker.databinding.FragmentJourneyBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class JourneyFragment : Fragment() {
    private var _binding: FragmentJourneyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JourneyViewModel by viewModels()
    private lateinit var journeyAdapter: JourneyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJourneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        journeyAdapter = JourneyAdapter(
            onFeelingClick = { summary ->
                // TODO: Show feeling picker dialog
            },
            onMediaClick = { summary ->
                // TODO: Open media picker
            },
            onJournalClick = { summary ->
                // TODO: Open journal entry dialog
            }
        )

        binding.rvJourneyTimeline.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = journeyAdapter
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.daySummaries.collectLatest { summaries ->
                journeyAdapter.submitList(summaries)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}