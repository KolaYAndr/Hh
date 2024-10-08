package com.effective.network.data.remote.model

data class Response(
    val offers: List<OfferDto>,
    val vacancies: List<VacancyDto>
)