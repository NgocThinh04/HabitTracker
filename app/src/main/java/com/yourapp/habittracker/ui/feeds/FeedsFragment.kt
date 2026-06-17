package com.yourapp.habittracker.ui.feeds

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yourapp.habittracker.R
import com.yourapp.habittracker.databinding.FragmentFeedsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FeedsFragment : Fragment() {
    private var _binding: FragmentFeedsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FeedsViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
        setupTabListeners()
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter { post, reaction ->
            viewModel.addReaction(post.id, reaction)
        }

        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }
    }

    private fun observeData() {
        // Observe posts
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.posts.collectLatest { posts ->
                postAdapter.submitList(posts)
            }
        }

        // Observe current tab
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentTab.collectLatest { tab ->
                updateTabUI(tab)
            }
        }
    }

    private fun setupTabListeners() {
        binding.apply {
            tvTabPublic.setOnClickListener { viewModel.setTab("public") }
            tvTabFriends.setOnClickListener { viewModel.setTab("friends") }
            tvTabMine.setOnClickListener { viewModel.setTab("mine") }
        }
    }

    private fun updateTabUI(selectedTab: String) {
        binding.apply {
            // Reset tất cả tabs
            resetTab(tvTabPublic)
            resetTab(tvTabFriends)
            resetTab(tvTabMine)

            // Highlight tab được chọn
            when (selectedTab) {
                "public" -> highlightTab(tvTabPublic)
                "friends" -> highlightTab(tvTabFriends)
                "mine" -> highlightTab(tvTabMine)
            }
        }
    }

    private fun resetTab(tab: android.widget.TextView) {
        // Dùng GradientDrawable mới để đảm bảo không null
        val bg = android.graphics.drawable.GradientDrawable()
        bg.shape = android.graphics.drawable.GradientDrawable.RECTANGLE
        bg.cornerRadius = 8f * resources.displayMetrics.density
        bg.setColor(Color.TRANSPARENT)
        tab.background = bg
        tab.setTextColor(Color.parseColor("#888888"))
        tab.elevation = 0f
    }

    private fun highlightTab(tab: android.widget.TextView) {
        val bg = android.graphics.drawable.GradientDrawable()
        bg.shape = android.graphics.drawable.GradientDrawable.RECTANGLE
        bg.cornerRadius = 8f * resources.displayMetrics.density
        bg.setColor(Color.WHITE)
        tab.background = bg
        tab.setTextColor(Color.parseColor("#111111"))
        tab.elevation = 2f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}