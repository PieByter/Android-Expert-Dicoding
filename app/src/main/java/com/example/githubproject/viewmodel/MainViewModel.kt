package com.example.githubproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubproject.core.domain.usecase.SearchUsersUseCase
import com.githubproject.core.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val searchUsersUseCase: SearchUsersUseCase) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<User>>(emptyList())
    val searchResults: StateFlow<List<User>> = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                searchUsersUseCase.execute(query).collect { users ->
                    _searchResults.value = users
                }
            } catch (e: Exception) {
                // Handle the error appropriately
            } finally {
                _isLoading.value = false
            }
        }
    }
}

