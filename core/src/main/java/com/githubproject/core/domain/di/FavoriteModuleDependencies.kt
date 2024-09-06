package com.githubproject.core.domain.di

import com.githubproject.core.domain.usecase.GithubUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun provideGetAllFavoriteUserUseCase(): GithubUseCase
}
