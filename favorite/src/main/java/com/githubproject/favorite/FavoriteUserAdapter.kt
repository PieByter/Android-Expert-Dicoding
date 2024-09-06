package com.githubproject.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubproject.databinding.ItemUserBinding
import com.githubproject.core.domain.model.GithubUser

class FavoriteUserAdapter :
    ListAdapter<GithubUser, FavoriteUserAdapter.FavoriteUserViewHolder>(DiffCallback()) {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val githubUser = getItem(position)
        holder.bind(githubUser)
    }

    inner class FavoriteUserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(getItem(position).username)
                }
            }
        }

        fun bind(githubUser: GithubUser) {
            binding.tvUsername.text = githubUser.username
            Glide.with(binding.root.context)
                .load(githubUser.avatarUrl)
                .into(binding.rvAvatar)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(username: String)
    }

    private class DiffCallback : DiffUtil.ItemCallback<GithubUser>() {
        override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
            return oldItem == newItem
        }
    }
}
