package com.githubproject.core.domain.repository.interfaces

import com.githubproject.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun searchUsers(keyword: String): Flow<List<User>>
    fun getFollowers(username: String): Flow<List<User>>
    fun getFollowing(username: String): Flow<List<User>>
}
