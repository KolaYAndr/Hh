package com.effective.database.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancies")
data class VacancyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val company: String,
    val description: String?,
    @Embedded
    val experience: ExperienceEntity,
    @ColumnInfo(name = "published_date")
    val publishedDate: String,
    val questions: List<String>,
    val responsibilities: String,
    @Embedded
    val salary: SalaryEntity,
    val schedules: List<String>,
    val title: String,
    @Embedded
    val address: AddressEntity,
)
