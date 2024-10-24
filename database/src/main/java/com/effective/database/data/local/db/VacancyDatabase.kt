package com.effective.database.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.effective.database.data.local.converter.StringListConverter
import com.effective.database.data.local.dao.VacancyDao
import com.effective.database.data.local.model.VacancyEntity

@Database(entities = [VacancyEntity::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class VacancyDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao

    companion object {
        @Volatile
        private var INSTANCE : VacancyDatabase? = null

        private const val DATABASE_NAME = "vacancy_db"

        fun getDatabase(context: Context): VacancyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VacancyDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
