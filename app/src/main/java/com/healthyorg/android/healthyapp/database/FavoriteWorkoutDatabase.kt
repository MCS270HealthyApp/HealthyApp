package com.healthyorg.android.healthyapp.database

import android.content.Context
import androidx.room.*
import com.healthyorg.android.healthyapp.workoutClasses.Favorite_Workouts

@Database(entities = [Favorite_Workouts::class], version = 1)
abstract class FavoriteWorkoutDatabase : RoomDatabase() {
    abstract fun favoriteWorkoutDao(): FavoriteWorkoutDao

    companion object{
        @Volatile
        private var INSTANCE: FavoriteWorkoutDatabase? = null

        fun getDatabase(
            context: Context
        ): FavoriteWorkoutDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteWorkoutDatabase::class.java,
                    "favorite-workout-database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}