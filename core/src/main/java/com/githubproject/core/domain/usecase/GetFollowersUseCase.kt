package com.githubproject.core.domain.usecase

import com.githubproject.core.domain.model.User
import com.githubproject.core.domain.repository.interfaces.UserRepository
import kotlinx.coroutines.flow.Flow

class GetFollowersUseCase(private val userRepository: UserRepository) {
    operator fun invoke(username: String): Flow<List<User>> {
        return userRepository.getFollowers(username)
    }
}
