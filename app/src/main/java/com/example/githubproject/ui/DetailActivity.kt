package com.example.githubproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubproject.R
import com.example.githubproject.adapter.SectionsPagerAdapter
import com.example.githubproject.databinding.ActivityDetailBinding
import com.example.githubproject.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    private var userAvatarUrl: String = ""
    private var username: String? = null

    companion object {
        private const val EXTRA_USERNAME = "username"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            observeViewModel()
            username?.let {
                viewModel.getDetailUserResponse(it)
                viewModel.checkIfUserIsFavorite(it)
            }

            binding.btnFavorite.setOnClickListener {
                if (viewModel.isFavorite.value == true) {
                    viewModel.removeFromFavorites(username.orEmpty())
                    Toast.makeText(this, "User removed from favorites", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.userDetail.value?.let { detailUser ->
                        viewModel.addToFavorites(detailUser)
                        Toast.makeText(this, "User added to favorites", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            binding.btnShare.setOnClickListener {
                val githubUrl = "https://github.com/$username"
                shareGithubUrl(githubUrl)
            }
        } else {
            Toast.makeText(this, "Username is null", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.apply {
                detailProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                val visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
                imageAvatar.visibility = visibility
                textName.visibility = visibility
                textUsername.visibility = visibility
                textFollowers.visibility = visibility
                textFollowing.visibility = visibility
                tabs.visibility = visibility
                viewPager.visibility = visibility
                btnFavorite.visibility = visibility
                btnShare.visibility = visibility
            }
        }

        viewModel.userDetail.observe(this) { detailUser ->
            binding.apply {
                textName.text = detailUser.name ?: "Unknown"
                textFollowers.text = getString(R.string.followers, detailUser.followers ?: 0)
                textFollowing.text = getString(R.string.following, detailUser.following ?: 0)
                textUsername.text = detailUser.username
                Glide.with(this@DetailActivity)
                    .load(detailUser.avatarUrl)
                    .into(imageAvatar)
            }

            userAvatarUrl = detailUser.avatarUrl ?: ""

            sectionsPagerAdapter = SectionsPagerAdapter(this)
            sectionsPagerAdapter.username = detailUser.username.orEmpty()

            binding.viewPager.adapter = sectionsPagerAdapter

            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Followers"
                    1 -> "Following"
                    else -> null
                }
            }.attach()
        }

        viewModel.isFavorite.observe(this) { isFavorite ->
            binding.btnFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_full else R.drawable.ic_favorite_border
            )
        }
    }

    private fun shareGithubUrl(githubUrl: String?) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, githubUrl)
        }
        startActivity(Intent.createChooser(shareIntent, "Share GitHub URL"))
    }
}
