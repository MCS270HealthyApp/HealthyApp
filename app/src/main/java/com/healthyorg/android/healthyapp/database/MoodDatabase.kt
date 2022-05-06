package com.healthyorg.android.healthyapp.database

import android.content.Context
import androidx.room.*
import com.healthyorg.android.healthyapp.MoodClasses.Daily_Mood

//Creates the database to hold our mood objects
@Database(entities = [Daily_Mood::class], version = 1)
@TypeConverters(MoodTypeConverters::class)
abstract class MoodDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao

    companion object{
        @Volatile
        private var INSTANCE: MoodDatabase? = null

        fun getDatabase(
            context: Context
        ): MoodDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodDatabase::class.java,
                    "mood_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}