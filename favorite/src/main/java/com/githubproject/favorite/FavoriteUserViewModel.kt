package com.githubproject.favorite

import androidx.lifecycle.ViewModel
import com.githubproject.core.domain.model.GithubUser
import com.githubproject.core.domain.usecase.GithubUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteUserViewModel @Inject constructor(
    githubUseCase: GithubUseCase,
) : ViewModel() {

    val favoriteUsers: Flow<List<GithubUser>> = githubUseCase.getFavoriteUsers()

}
