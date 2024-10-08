package com.effective.core.domain.mappers

import android.icu.text.SimpleDateFormat
import com.effective.core.domain.model.Address
import com.effective.core.domain.model.Button
import com.effective.core.domain.model.Experience
import com.effective.core.domain.model.Offer
import com.effective.core.domain.model.Salary
import com.effective.core.domain.model.Vacancy
import com.effective.network.data.remote.model.AddressDto
import com.effective.network.data.remote.model.ButtonDto
import com.effective.network.data.remote.model.ExperienceDto
import com.effective.network.data.remote.model.OfferDto
import com.effective.network.data.remote.model.SalaryDto
import com.effective.network.data.remote.model.VacancyDto

fun VacancyDto.toDomain(inputFormatter: SimpleDateFormat, outputFormatter: SimpleDateFormat): Vacancy = Vacancy(
        address = address.toDomain(),
        appliedNumber = appliedNumber,
        company = company,
        description = description,
        experience = experience.toDomain(),
        id = id,
        isFavorite = isFavorite,
        lookingNumber = lookingNumber,
        publishedDate = outputFormatter.format(inputFormatter.parse(publishedDate)),
        questions = questions,
        responsibilities = responsibilities,
        salary = salary.toDomain(),
        schedules = schedules,
        title = title
)

fun AddressDto.toDomain(): Address = Address(house, street, town)
fun ButtonDto.toDomain(): Button = Button(text)
fun ExperienceDto.toDomain(): Experience = Experience(previewText, text)
fun SalaryDto.toDomain(): Salary = Salary(full, short)
fun OfferDto.toDomain(): Offer = Offer(button?.toDomain(), id, link, title)