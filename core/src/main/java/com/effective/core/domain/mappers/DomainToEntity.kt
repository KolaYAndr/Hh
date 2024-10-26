package com.effective.core.domain.mappers

import com.effective.core.domain.model.Address
import com.effective.core.domain.model.Experience
import com.effective.core.domain.model.Salary
import com.effective.core.domain.model.Vacancy
import com.effective.database.data.local.model.AddressEntity
import com.effective.database.data.local.model.ExperienceEntity
import com.effective.database.data.local.model.SalaryEntity
import com.effective.database.data.local.model.VacancyEntity

fun Vacancy.toEntity(): VacancyEntity = VacancyEntity(
    address = address.toEntity(),
    company = company,
    description = description,
    experience = experience.toEntity(),
    id = id,
    publishedDate = publishedDate,
    questions = questions,
    responsibilities = responsibilities,
    salary = salary.toEntity(),
    schedules = schedules,
    title = title
)


fun Address.toEntity(): AddressEntity = AddressEntity(house, street, town)
fun Experience.toEntity(): ExperienceEntity = ExperienceEntity(previewText, text)
fun Salary.toEntity(): SalaryEntity = SalaryEntity(full, short)
