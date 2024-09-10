package com.githubproject.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.githubproject.core.domain.usecase.GithubUseCase
import com.githubproject.favorite.FavoriteUserViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val getAllFavoriteUserUseCase: GithubUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteUserViewModel::class.java) -> FavoriteUserViewModel(getAllFavoriteUserUseCase) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
        }
}
