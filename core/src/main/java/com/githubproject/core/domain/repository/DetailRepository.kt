package com.githubproject.core.domain.repository

import com.githubproject.core.domain.data.remote.retrofit.ApiService
import com.githubproject.core.domain.model.DetailUser
import com.githubproject.core.domain.repository.interfaces.DetailUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val apiService: ApiService,
) : DetailUserRepository {

    override fun getUserDetails(username: String): Flow<DetailUser> = flow {
        try {
            val response = apiService.getDetailUser(username)
            response.let {
                emit(
                    DetailUser(
                        name = it.name,
                        username = it.login,
                        followers = it.followers,
                        following = it.following,
                        avatarUrl = it.avatarUrl,
                        htmlUrl = it.htmlUrl
                    )
                )
            }
        } catch (e: Exception) {
            throw Exception("Error fetching user details: ${e.message}")
        }
    }
}
