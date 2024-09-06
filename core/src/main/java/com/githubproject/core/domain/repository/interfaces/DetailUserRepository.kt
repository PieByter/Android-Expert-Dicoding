package com.githubproject.core.domain.repository.interfaces

import com.githubproject.core.domain.model.DetailUser
import kotlinx.coroutines.flow.Flow

interface DetailUserRepository {
    fun getUserDetails(username: String): Flow<DetailUser>
}
