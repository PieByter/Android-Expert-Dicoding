package com.githubproject.core.domain.repository

import com.githubproject.core.domain.data.local.entity.FavoriteUser
import com.githubproject.core.domain.data.local.room.FavoriteUserDao
import com.githubproject.core.domain.model.GithubUser
import com.githubproject.core.domain.repository.interfaces.IGithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class FavoriteUserRepository internal constructor(
    private val favoriteUserDao: FavoriteUserDao,
) : IGithubRepository {

    override fun getFavoriteUsers(): Flow<List<GithubUser>> {
        return favoriteUserDao.getAllFavoriteUsers().map { favoriteUsers ->
            favoriteUsers.map {
                GithubUser(
                    username = it.username,
                    avatarUrl = it.avatarUrl,
                    isFavorite = it.isFavorite
                )
            }
        }
    }

    override fun getFavoriteUserByUsername(username: String): Flow<GithubUser?> {
        return favoriteUserDao.getFavoriteUserByUsername(username).map { favoriteUser ->
            favoriteUser?.let {
                GithubUser(
                    username = it.username,
                    avatarUrl = it.avatarUrl,
                    isFavorite = it.isFavorite
                )
            }
        }
    }

    override suspend fun insertFavoriteUser(githubUser: GithubUser) {
        withContext(Dispatchers.IO) {
            favoriteUserDao.insert(
                FavoriteUser(
                    username = githubUser.username,
                    avatarUrl = githubUser.avatarUrl,
                    isFavorite = githubUser.isFavorite
                )
            )
        }
    }

    override suspend fun deleteFavoriteUser(githubUser: GithubUser) {
        withContext(Dispatchers.IO) {
            favoriteUserDao.delete(
                FavoriteUser(
                    username = githubUser.username,
                    avatarUrl = githubUser.avatarUrl,
                    isFavorite = githubUser.isFavorite
                )
            )
        }
    }

    override suspend fun deleteFavoriteUserByUsername(username: String) {
        withContext(Dispatchers.IO) {
            favoriteUserDao.deleteByUsername(username)
        }
    }
}
