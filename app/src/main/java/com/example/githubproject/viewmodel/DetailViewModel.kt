package com.example.githubproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubproject.core.domain.usecase.DetailUseCase
import com.githubproject.core.domain.model.DetailUser
import com.githubproject.core.domain.model.GithubUser
import com.githubproject.core.domain.usecase.GithubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: DetailUseCase,
    private val githubUseCase: GithubUseCase,
) : ViewModel() {

    private val _userDetail = MutableLiveData<DetailUser>()
    val userDetail: LiveData<DetailUser> get() = _userDetail

    private val _errorMessage = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun getDetailUserResponse(username: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                detailUseCase.getUserDetails(username).collect { user ->
                    _isLoading.value = false
                    _userDetail.postValue(user)
                    checkIfUserIsFavorite(username)
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.postValue("Error: ${e.message}")
            }
        }
    }

    fun checkIfUserIsFavorite(username: String) {
        viewModelScope.launch {
            githubUseCase.getFavoriteUserByUsername(username)
                .firstOrNull()
                ?.let {
                    _isFavorite.postValue(true)
                } ?: run {
                _isFavorite.postValue(false)
            }
        }
    }

    fun addToFavorites(detailUser: DetailUser) {
        val githubUser = detailUser.toGithubUser(isFavorite = true)
        viewModelScope.launch {
            githubUseCase.insertFavoriteUser(githubUser)
            _isFavorite.value = true
        }
    }

    fun removeFromFavorites(username: String) {
        viewModelScope.launch {
            githubUseCase.deleteFavoriteUserByUsername(username)
            _isFavorite.value = false
        }
    }

    private fun DetailUser.toGithubUser(isFavorite: Boolean): GithubUser {
        return GithubUser(
            username = this.username ?: "",
            avatarUrl = this.avatarUrl,
            isFavorite = isFavorite
        )
    }
}
