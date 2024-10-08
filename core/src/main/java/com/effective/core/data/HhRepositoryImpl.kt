package com.effective.core.data

import android.icu.text.SimpleDateFormat
import com.effective.core.domain.mappers.toDomain
import com.effective.core.domain.model.Offer
import com.effective.core.domain.model.Vacancy
import com.effective.core.domain.repository.HhRepository
import com.effective.network.data.remote.api.ApiService
import com.effective.network.data.remote.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

class HhRepositoryImpl @Inject constructor(private val api: ApiService) : HhRepository {
    private val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val outputFormatter = SimpleDateFormat("d MMMM", Locale.getDefault())
    private var response: Response? = null

    private suspend fun getInfo(): Response = withContext(Dispatchers.IO) {
        if (response == null) {
            response = api.getInfo()
        }
        response!!
    }

    override suspend fun getVacancies(): List<Vacancy> = withContext(Dispatchers.IO) {
        val result = response?.vacancies ?: getInfo().vacancies
        result.map { it.toDomain(inputFormatter, outputFormatter) }
    }

    override suspend fun getOffers(): List<Offer>  = withContext(Dispatchers.IO) {
        val result = response?.offers ?: getInfo().offers
        result.map { it.toDomain() }
    }
}