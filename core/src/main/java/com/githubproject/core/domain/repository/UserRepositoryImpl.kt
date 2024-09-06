package com.githubproject.core.domain.repository

import com.githubproject.core.domain.data.remote.retrofit.ApiService
import com.githubproject.core.domain.model.User
import com.githubproject.core.domain.repository.interfaces.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository {

    override fun searchUsers(keyword: String): Flow<List<User>> = flow {
        val response = apiService.searchUsers(keyword).items!!.map {
            User(
                username = it?.login,
                avatarUrl = it?.avatarUrl,
                htmlUrl = it?.htmlUrl
            )
        }
        emit(response)
    }.flowOn(Dispatchers.IO)

    override fun getFollowers(username: String): Flow<List<User>> = flow {
        val response = apiService.getFollowers(username).map {
            User(
                username = it.login,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl
            )
        }
        emit(response)
    }.flowOn(Dispatchers.IO)

    override fun getFollowing(username: String): Flow<List<User>> = flow {
        val response = apiService.getFollowing(username).map {
            User(
                username = it.login,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl
            )
        }
        emit(response)
    }.flowOn(Dispatchers.IO)
}
