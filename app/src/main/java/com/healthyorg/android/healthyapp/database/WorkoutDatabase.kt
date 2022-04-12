package com.healthyorg.android.healthyapp.database

import android.content.Context
import androidx.room.*
import com.healthyorg.android.healthyapp.workoutClasses.Daily_Workout

@Database(entities = [Daily_Workout::class], version = 4)
@TypeConverters(WorkoutTypeConverters::class)
abstract class WorkoutDatabase: RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao

    companion object{
        @Volatile
        private var INSTANCE: WorkoutDatabase? = null

        fun getDatabase(
            context: Context
        ): WorkoutDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutDatabase::class.java,
                    "workout_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}