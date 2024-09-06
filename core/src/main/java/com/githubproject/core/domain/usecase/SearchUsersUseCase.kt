package com.githubproject.core.domain.usecase

import com.githubproject.core.domain.model.User
import com.githubproject.core.domain.repository.interfaces.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    fun execute(keyword: String): Flow<List<User>> = userRepository.searchUsers(keyword)
}
