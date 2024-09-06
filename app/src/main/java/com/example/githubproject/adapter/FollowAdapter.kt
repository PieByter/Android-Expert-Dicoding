package com.example.githubproject.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubproject.databinding.ItemFollowBinding
import com.githubproject.core.domain.model.User
import com.example.githubproject.ui.DetailActivity

class FollowAdapter(private val context: Context) :
    ListAdapter<User, FollowAdapter.FollowerFollowingViewHolder>(DiffCallback()) {

    inner class FollowerFollowingViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                textUsername.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(imageAvatar)

                itemView.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("username", user.username)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerFollowingViewHolder {
        val binding = ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerFollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerFollowingViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    fun setData(data: List<User>) {
        submitList(data)
    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
