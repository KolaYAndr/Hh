package com.effective.hh

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.effective.core.domain.repository.HhRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: HhRepository): ViewModel() {
    private val _uiState: MutableStateFlow<MainUiState> =
        MutableStateFlow(MainUiState(0))
    val uiState: StateFlow<MainUiState> get() = _uiState.asStateFlow()

    private fun getFavourites() {
        viewModelScope.launch {
            val vacancies = repository.getVacancies()
            _uiState.update { MainUiState(vacancies.count { it.isFavorite }) }
        }
    }

    init {
        getFavourites()
    }
}

data class MainUiState(
    val likedVacancies: Int
)
