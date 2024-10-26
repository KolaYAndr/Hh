package com.effective.database.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.effective.database.data.local.model.VacancyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VacancyDao {

    @Upsert
    suspend fun upsertVacancy(vacancyEntity: VacancyEntity)

    @Insert
    suspend fun insertVacancy(vacancyEntity: VacancyEntity)

    @Delete
    suspend fun deleteVacancy(vacancyEntity: VacancyEntity)

    @Query("select * from vacancies")
    fun getLikedVacancies() : Flow<List<VacancyEntity>>
}
