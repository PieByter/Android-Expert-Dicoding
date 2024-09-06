package com.githubproject.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubproject.core.domain.di.FavoriteModuleDependencies
import com.example.githubproject.ui.DetailActivity
import com.githubproject.core.domain.usecase.GithubUseCase
import com.githubproject.favorite.databinding.ActivityFavoriteUserBinding
import com.githubproject.favorite.di.DaggerFavoriteComponent
import com.githubproject.favorite.di.ViewModelFactory
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteUserActivity : AppCompatActivity(), FavoriteUserAdapter.OnItemClickListener {

    private var _binding: ActivityFavoriteUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteUserAdapter

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteUserViewModel: FavoriteUserViewModel by viewModels { factory }

    @Inject
    lateinit var githubUseCase: GithubUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Dagger component
        DaggerFavoriteComponent.builder()
            .context(this)
            .favoriteModuleDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = FavoriteUserAdapter()
        binding.rvFavoriteUser.adapter = adapter
        binding.rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        adapter.setOnItemClickListener(this)
    }

    private fun observeViewModel() {
        // Collect flow data using lifecycleScope
        lifecycleScope.launch {
            favoriteUserViewModel.favoriteUsers.collect { favoriteUsers ->
                if (favoriteUsers.isEmpty()) {
                    binding.tvNoData.visibility = View.VISIBLE
                    binding.rvFavoriteUser.visibility = View.GONE
                } else {
                    binding.tvNoData.visibility = View.GONE
                    binding.rvFavoriteUser.visibility = View.VISIBLE
                    adapter.submitList(favoriteUsers)
                }
            }
        }
    }

    override fun onItemClick(username: String) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("username", username)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
