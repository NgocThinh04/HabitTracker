package com.yourapp.habittracker.ui.feeds


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yourapp.habittracker.R
import com.yourapp.habittracker.data.local.entity.PostEntity
import com.yourapp.habittracker.databinding.ItemPostBinding
import org.json.JSONObject

class PostAdapter(
    private val onReactionClick: (PostEntity, String) -> Unit
) : ListAdapter<PostEntity, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostEntity) {
            binding.apply {
                // Username và thời gian
                tvUsername.text = "${post.username} ${post.countryEmoji} ${post.timeAgo}"

                // Streak info
                tvStreakInfo.text = "Day ${post.streakDay} of ${post.totalDays} Days Challenge"

                // Mô tả
                tvPostDescription.text = post.description

                // Location
                if (!post.location.isNullOrEmpty()) {
                    tvPostLocation.text = post.location
                    tvPostLocation.visibility = View.VISIBLE
                    ivPostImage.visibility = View.GONE
                } else if (!post.imageUrl.isNullOrEmpty()) {
                    tvPostLocation.visibility = View.GONE
                    ivPostImage.visibility = View.VISIBLE
                    // TODO: Load ảnh bằng Glide/Picasso
                    // Glide.with(ivPostImage).load(post.imageUrl).into(ivPostImage)
                } else {
                    tvPostLocation.visibility = View.VISIBLE
                    tvPostLocation.text = "No media"
                }

                // Avatar (màu ngẫu nhiên dựa trên username)
                val avatarColors = listOf(
                    "#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4",
                    "#FFEAA7", "#DDA0DD", "#98D8C8", "#F7DC6F"
                )
                val colorIndex = post.username.hashCode().rem(avatarColors.size).let {
                    if (it < 0) it + avatarColors.size else it
                }
                ivAvatar.background.setTint(Color.parseColor(avatarColors[colorIndex]))

                // Reactions
                setupReactions(post)

                // More button
                tvMore.setOnClickListener {
                    // TODO: Show options (report, hide, etc.)
                }
            }
        }

        private fun setupReactions(post: PostEntity) {
            binding.apply {
                val reactions = try {
                    JSONObject(post.reactions)
                } catch (e: Exception) {
                    JSONObject()
                }

                val clapCount = reactions.optInt("👏", 0)
                val fireCount = reactions.optInt("🔥", 0)
                val strongCount = reactions.optInt("💪", 0)
                val heartCount = reactions.optInt("❤️", 0)

                tvReactionClap.text = if (clapCount > 0) "👏 $clapCount" else "👏"
                tvReactionFire.text = if (fireCount > 0) "🔥 $fireCount" else "🔥"
                tvReactionStrong.text = if (strongCount > 0) "💪 $strongCount" else "💪"
                tvReactionHeart.text = if (heartCount > 0) "❤️ $heartCount" else "❤️"

                tvReactionClap.setOnClickListener { onReactionClick(post, "👏") }
                tvReactionFire.setOnClickListener { onReactionClick(post, "🔥") }
                tvReactionStrong.setOnClickListener { onReactionClick(post, "💪") }
                tvReactionHeart.setOnClickListener { onReactionClick(post, "❤️") }
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<PostEntity>() {
    override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return oldItem == newItem
    }
}