package com.githubproject.core.domain.model

data class GithubUser(
    val username: String,
    val avatarUrl: String?,
    val isFavorite: Boolean
)
