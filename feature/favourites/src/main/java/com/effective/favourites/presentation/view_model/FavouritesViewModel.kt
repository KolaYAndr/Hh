package com.effective.favourites.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.effective.core.domain.model.Vacancy
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
class FavouritesViewModel @Inject constructor(private val repository: HhRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<FavouritesUiState> =
        MutableStateFlow(FavouritesUiState(emptyList()))
    val uiState: StateFlow<FavouritesUiState> get() = _uiState.asStateFlow()

    private fun getFavourites() {
        viewModelScope.launch {
            try {
                repository.getLikedVacancies().collectLatest { vacancies ->
                    _uiState.update { it.copy(likedVacancies = vacancies) }
                }
            } catch (e: IOException) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    fun pressLike(vacancy: Vacancy) {
        if (vacancy.isFavorite) deleteVacancy(vacancy) else likeVacancy(vacancy)
    }

    private fun likeVacancy(vacancy: Vacancy) {
        viewModelScope.launch {
            repository.likeVacancy(vacancy)
        }
    }

    private fun deleteVacancy(vacancy: Vacancy) {
        viewModelScope.launch {
            repository.deleteVacancy(vacancy)
        }
    }

    init {
        getFavourites()
    }
}

data class FavouritesUiState(
    val likedVacancies: List<Vacancy>,
    val errorMessage: String? = null
)
