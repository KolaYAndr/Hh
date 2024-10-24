package com.effective.core.data

import android.icu.text.SimpleDateFormat
import com.effective.core.domain.mappers.toDomain
import com.effective.core.domain.mappers.toEntity
import com.effective.core.domain.model.Offer
import com.effective.core.domain.model.Vacancy
import com.effective.core.domain.repository.HhRepository
import com.effective.database.data.local.dao.VacancyDao
import com.effective.network.data.remote.api.ApiService
import com.effective.network.data.remote.model.Response
import com.effective.network.data.remote.model.VacancyDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

class HhRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: VacancyDao
) : HhRepository {
    private val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val outputFormatter = SimpleDateFormat("d MMMM", Locale.getDefault())
    private var response: Response? = null

    private suspend fun getInfo(): Response = withContext(Dispatchers.IO) {
        if (response == null) {
            val info = api.getInfo()
            response = info
            cacheInfo(info.vacancies)
        }
        response!!
    }

    private suspend fun cacheInfo(info: List<VacancyDto>) = withContext(Dispatchers.IO) {
        info.filter { it.isFavorite }.forEach { dao.upsertVacancy(it.toEntity())}
    }

    override suspend fun getVacancies(): List<Vacancy> = withContext(Dispatchers.IO) {
        val result = response?.vacancies ?: getInfo().vacancies
        result.map { it.toDomain(inputFormatter, outputFormatter) }
    }

    override suspend fun getOffers(): List<Offer> = withContext(Dispatchers.IO) {
        val result = response?.offers ?: getInfo().offers
        result.map { it.toDomain() }
    }
}