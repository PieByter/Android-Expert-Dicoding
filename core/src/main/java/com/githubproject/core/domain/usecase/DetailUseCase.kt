package com.githubproject.core.domain.usecase

import com.githubproject.core.domain.model.DetailUser
import com.githubproject.core.domain.repository.interfaces.DetailUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailUseCase @Inject constructor(
    private val detailUserRepository: DetailUserRepository
) {
    fun getUserDetails(username: String): Flow<DetailUser> = detailUserRepository.getUserDetails(username)
}
