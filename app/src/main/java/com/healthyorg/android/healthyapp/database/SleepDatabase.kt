package com.healthyorg.android.healthyapp.database

import android.content.Context
import androidx.room.*
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood

//Creates the database to hold our sleep objects, based on our dao
@Database(entities = [DailySleepMood::class], version = 1)
@TypeConverters(SleepTypeConverters::class)
abstract class SleepDatabase : RoomDatabase() {
    abstract fun sleepDao(): SleepDao

    companion object{
        @Volatile
        private var INSTANCE: SleepDatabase? = null

        fun getDatabase(
            context: Context
        ): SleepDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SleepDatabase::class.java,
                    "sleep_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}