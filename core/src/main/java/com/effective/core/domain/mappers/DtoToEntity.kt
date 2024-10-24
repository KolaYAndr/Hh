package com.effective.core.domain.mappers

import com.effective.database.data.local.model.AddressEntity
import com.effective.database.data.local.model.ExperienceEntity
import com.effective.database.data.local.model.SalaryEntity
import com.effective.database.data.local.model.VacancyEntity
import com.effective.network.data.remote.model.AddressDto
import com.effective.network.data.remote.model.ExperienceDto
import com.effective.network.data.remote.model.SalaryDto
import com.effective.network.data.remote.model.VacancyDto

fun VacancyDto.toEntity(): VacancyEntity = VacancyEntity(
    address = address.toEntity(),
    company = company,
    description = description,
    experience = experience.toEntity(),
    id = id,
    isFavorite = isFavorite,
    publishedDate = publishedDate,
    questions = questions,
    responsibilities = responsibilities,
    salary = salary.toEntity(),
    schedules = schedules,
    title = title
)


fun AddressDto.toEntity(): AddressEntity = AddressEntity(house, street, town)
fun ExperienceDto.toEntity(): ExperienceEntity = ExperienceEntity(previewText, text)
fun SalaryDto.toEntity(): SalaryEntity = SalaryEntity(full, short)
