package com.example.githubproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubproject.core.domain.model.User
import com.githubproject.core.domain.repository.interfaces.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _followersFollowing = MutableLiveData<List<User>?>()
    val followersFollowing: LiveData<List<User>?>
        get() = _followersFollowing

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getFollowersFollowing(position: Int, username: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val flow = if (position == 1) {
                    userRepository.getFollowers(username)
                } else {
                    userRepository.getFollowing(username)
                }
                flow.collect { followersFollowing ->
                    _followersFollowing.postValue(followersFollowing)
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                _isLoading.value = false
            }
        }
    }
}
