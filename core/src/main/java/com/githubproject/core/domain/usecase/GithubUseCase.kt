package com.githubproject.core.domain.usecase

import com.githubproject.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubUseCase {
    fun getFavoriteUsers(): Flow<List<GithubUser>>
    fun getFavoriteUserByUsername(username: String): Flow<GithubUser?>
    suspend fun insertFavoriteUser(githubUser: GithubUser)
    suspend fun deleteFavoriteUser(githubUser: GithubUser)
    suspend fun deleteFavoriteUserByUsername(username: String)
}
