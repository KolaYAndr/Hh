package com.effective.network.data.remote.model

data class VacancyDto(
    val address: AddressDto,
    val appliedNumber: Int?,
    val company: String,
    val description: String?,
    val experience: ExperienceDto,
    val id: String,
    val isFavorite: Boolean,
    val lookingNumber: Int?,
    val publishedDate: String,
    val questions: List<String>,
    val responsibilities: String,
    val salary: SalaryDto,
    val schedules: List<String>,
    val title: String
)