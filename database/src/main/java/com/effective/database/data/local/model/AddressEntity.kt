package com.effective.database.data.local.model

import androidx.room.Entity

@Entity
data class AddressEntity(
    val house: String,
    val street: String,
    val town: String
)
