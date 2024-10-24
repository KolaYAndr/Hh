package com.effective.database.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class ExperienceEntity(
    @ColumnInfo(name = "preview_text")
    val previewText: String,
    val text: String
)
