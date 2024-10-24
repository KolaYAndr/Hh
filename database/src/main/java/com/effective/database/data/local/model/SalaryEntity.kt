package com.effective.database.data.local.model

import androidx.room.Entity

@Entity
data class SalaryEntity(
    val full: String,
    val short: String?
)
