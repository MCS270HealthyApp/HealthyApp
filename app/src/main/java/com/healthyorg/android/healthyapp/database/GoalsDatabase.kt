package com.healthyorg.android.healthyapp.database

import android.content.Context
import androidx.room.*
import com.healthyorg.android.healthyapp.goalClasses.Goal

@Database(entities = [Goal::class], version = 2)
abstract class GoalsDatabase : RoomDatabase() {
    abstract fun GoalDao(): GoalDao
    companion object{
        @Volatile
        private var INSTANCE: GoalsDatabase? = null

        fun getDatabase(
            context: Context
        ): GoalsDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GoalsDatabase::class.java,
                    "goals_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}