package com.effective.network.data.remote.api

import com.effective.network.data.remote.model.Response
import retrofit2.http.GET

interface ApiService {
    @GET("u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download")
    suspend fun getInfo(): Response
}