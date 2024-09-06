package com.githubproject.core.domain.repository.interfaces

import com.githubproject.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface IGithubRepository {
    fun getFavoriteUsers(): Flow<List<GithubUser>>
    fun getFavoriteUserByUsername(username: String): Flow<GithubUser?>
    suspend fun insertFavoriteUser(githubUser: GithubUser)
    suspend fun deleteFavoriteUser(githubUser: GithubUser)
    suspend fun deleteFavoriteUserByUsername(username: String)
}
