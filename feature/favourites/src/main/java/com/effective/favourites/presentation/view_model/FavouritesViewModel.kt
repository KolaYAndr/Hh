package com.effective.favourites.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.effective.core.domain.model.Vacancy
import com.effective.core.domain.repository.HhRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repository: HhRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<FavouritesUiState> =
        MutableStateFlow(FavouritesUiState(emptyList()))
    val uiState: StateFlow<FavouritesUiState> get() = _uiState.asStateFlow()

    private fun getFavourites() {
        viewModelScope.launch {
            val vacancies = repository.getVacancies()
            _uiState.update { FavouritesUiState(vacancies.filter { it.isFavorite }) }
        }
    }

    init {
        getFavourites()
    }
}

data class FavouritesUiState(
    val likedVacancies: List<Vacancy>
)