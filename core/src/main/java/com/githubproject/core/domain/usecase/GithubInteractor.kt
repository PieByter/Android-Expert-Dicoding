package com.githubproject.core.domain.usecase

import com.githubproject.core.domain.model.GithubUser
import com.githubproject.core.domain.repository.interfaces.IGithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubInteractor @Inject constructor(private val githubRepository: IGithubRepository) :
    GithubUseCase {

    override fun getFavoriteUsers(): Flow<List<GithubUser>> {
        return githubRepository.getFavoriteUsers()
    }

    override fun getFavoriteUserByUsername(username: String): Flow<GithubUser?> {
        return githubRepository.getFavoriteUserByUsername(username)
    }

    override suspend fun insertFavoriteUser(githubUser: GithubUser) {
        githubRepository.insertFavoriteUser(githubUser)
    }

    override suspend fun deleteFavoriteUser(githubUser: GithubUser) {
        githubRepository.deleteFavoriteUser(githubUser)
    }

    override suspend fun deleteFavoriteUserByUsername(username: String) {
        githubRepository.deleteFavoriteUserByUsername(username)
    }
}
