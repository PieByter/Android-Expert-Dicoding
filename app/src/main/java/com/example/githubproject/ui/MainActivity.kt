package com.example.githubproject.ui

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubproject.R
import com.githubproject.core.domain.datastore.SettingPreferences
import com.example.githubproject.adapter.GithubAdapter
import com.example.githubproject.databinding.ActivityMainBinding
import com.example.githubproject.viewmodel.MainViewModel
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var settingPreferences: SettingPreferences

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var githubAdapter: GithubAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var splitInstallManager: SplitInstallManager
    private val viewModel: MainViewModel by viewModels()

    companion object {
        private const val DARK_MODE_REQUEST = 101
        private const val FAVORITE_MODULE = "favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        splitInstallManager = SplitInstallManagerFactory.create(this)

        lifecycleScope.launch {
            settingPreferences.getThemeSetting().collect { isDarkModeActive ->
                updateTheme(isDarkModeActive)
            }
        }

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        setupRecyclerView()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            val defaultQuery = sharedPreferences.getString("QUERY", "Pieby") ?: "Pieby"
            viewModel.searchUsers(defaultQuery)

            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu1 -> {
                        installAndOpenFavoriteModule()
                        true
                    }

                    R.id.menu2 -> {
                        val intent = Intent(this@MainActivity, DarkModeActivity::class.java)
                        startActivityLauncher.launch(intent)
                        true
                    }

                    else -> false
                }
            }

            searchView.editText.setOnEditorActionListener { textView, _, _ ->
                val query = textView.text.toString()
                searchBar.hint = query
                searchBar.setText(query)
                sharedPreferences.edit().putString("QUERY", query).apply()
                viewModel.searchUsers(query)
                false
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            }
        }

        lifecycleScope.launch {
            viewModel.searchResults.collect { users ->
                githubAdapter.submitList(users)
                binding.searchView.hide()
            }
        }
    }

    private fun setupRecyclerView() {
        githubAdapter = GithubAdapter()
        binding.rvUsers.apply {
            adapter = githubAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        githubAdapter.setOnItemClickListener { user ->
            user.htmlUrl?.let { user.username?.let { it1 -> navigateToDetailActivity(it1) } }
        }
    }

    private fun navigateToDetailActivity(username: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }

    private fun installAndOpenFavoriteModule() {
        if (splitInstallManager.installedModules.contains(FAVORITE_MODULE)) {
            moveToFavoriteUserActivity()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(FAVORITE_MODULE)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success installing module", Toast.LENGTH_SHORT).show()
                    moveToFavoriteUserActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing module", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToFavoriteUserActivity() {
        try {
            val intent =
                Intent(this, Class.forName("com.githubproject.favorite.FavoriteUserActivity"))
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            Toast.makeText(this, "Activity not found", Toast.LENGTH_SHORT).show()
        }
    }

    private val startActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val defaultQuery = sharedPreferences.getString("QUERY", "Pieby") ?: "Pieby"
            viewModel.searchUsers(defaultQuery)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DARK_MODE_REQUEST && resultCode == Activity.RESULT_OK) {
            val defaultQuery = sharedPreferences.getString("QUERY", "Pieby") ?: "Pieby"
            viewModel.searchUsers(defaultQuery)
        }
    }

    private fun updateTheme(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
