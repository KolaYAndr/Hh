package com.effective.core.domain.mappers

import com.effective.core.domain.model.Address
import com.effective.core.domain.model.Experience
import com.effective.core.domain.model.Salary
import com.effective.core.domain.model.Vacancy
import com.effective.database.data.local.model.AddressEntity
import com.effective.database.data.local.model.ExperienceEntity
import com.effective.database.data.local.model.SalaryEntity
import com.effective.database.data.local.model.VacancyEntity

fun VacancyEntity.toDomain() = Vacancy(
    address = address.toDomain(),
    appliedNumber = null,
    company = company,
    description = description,
    experience = experience.toDomain(),
    id = id,
    isFavorite = true,
    lookingNumber = null,
    publishedDate = publishedDate,
    questions = questions,
    responsibilities = responsibilities,
    salary = salary.toDomain(),
    schedules = schedules,
    title = title
)

fun AddressEntity.toDomain() = Address(house, street, town)
fun ExperienceEntity.toDomain() = Experience(previewText, text)
fun SalaryEntity.toDomain() = Salary(full, short)
