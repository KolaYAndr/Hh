package com.effective.database.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.effective.database.data.local.model.VacancyEntity

@Dao
interface VacancyDao {

    @Upsert
    suspend fun upsertVacancy(vacancyEntity: VacancyEntity)

    @Delete
    suspend fun deleteVacancy(vacancyEntity: VacancyEntity)

    @Query("select * from vacancies")
    suspend fun getAllVacancies() : List<VacancyEntity>
}
