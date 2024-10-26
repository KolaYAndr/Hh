package com.effective.core.domain.repository

import com.effective.core.domain.model.Offer
import com.effective.core.domain.model.Vacancy
import kotlinx.coroutines.flow.Flow

interface HhRepository {
    suspend fun getVacancies(): List<Vacancy>

    suspend fun getOffers(): List<Offer>

    fun getLikedVacancies(): Flow<List<Vacancy>>

    suspend fun upsertVacancy(vacancy: Vacancy)

    suspend fun likeVacancy(vacancy: Vacancy)

    suspend fun deleteVacancy(vacancy: Vacancy)
}
