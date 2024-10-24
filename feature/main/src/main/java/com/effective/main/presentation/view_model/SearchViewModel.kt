package com.effective.main.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.effective.core.domain.model.Offer
import com.effective.core.domain.model.Vacancy
import com.effective.core.domain.repository.HhRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: HhRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> get() = _uiState.asStateFlow()

    init {
        fetchInfo()
    }

    private fun fetchInfo() {
        viewModelScope.launch {
            try {
                val vacancies = repository.getVacancies()
                val offers = repository.getOffers()
                _uiState.update {
                    SearchUiState(
                        offers = offers,
                        vacancies = vacancies,
                        showAllVacancies = false
                    )
                }
            } catch (e: IOException) {
                _uiState.update {
                    it.copy(errorMessage = e.message)
                }
            }
        }
    }

    fun showMore() {
        _uiState.update {
            it.copy(
                showAllVacancies = true
            )
        }
    }

    fun showLess() {
        _uiState.update {
            it.copy(
                showAllVacancies = false
            )
        }
    }
}

data class SearchUiState(
    val offers: List<Offer> = emptyList(),
    val vacancies: List<Vacancy> = emptyList(),
    val showAllVacancies: Boolean = false,
    val errorMessage: String? = null
)
