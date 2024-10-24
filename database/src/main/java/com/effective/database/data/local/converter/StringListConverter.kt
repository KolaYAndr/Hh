package com.effective.database.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson

class StringListConverter {
    @TypeConverter
    fun fromStringToList(value: String): List<String> =
        Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun fromListToString(value: List<String>): String = Gson().toJson(value)
}
