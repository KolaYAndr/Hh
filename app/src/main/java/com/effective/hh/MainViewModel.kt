package com.effective.hh

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.effective.core.domain.repository.HhRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: HhRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<MainUiState> =
        MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> get() = _uiState.asStateFlow()

    private fun getFavourites() {
        viewModelScope.launch {
            try {
                repository.getLikedVacancies().collectLatest { vacancies ->
                    val count = vacancies.count()
                    _uiState.update { it.copy(likedVacancies = count) }
                }
            } catch (e: IOException) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    init {
        getFavourites()
    }
}

data class MainUiState(
    val likedVacancies: Int = 0,
    val errorMessage: String? = null
)
