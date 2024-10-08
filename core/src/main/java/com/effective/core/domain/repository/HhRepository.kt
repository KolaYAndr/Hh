package com.effective.core.domain.repository

import com.effective.core.domain.model.Offer
import com.effective.core.domain.model.Vacancy

interface HhRepository {
    suspend fun getVacancies(): List<Vacancy>

    suspend fun getOffers(): List<Offer>
}