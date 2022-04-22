package com.healthyorg.android.healthyapp.foodactivityclasses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.FavoriteFoodDatabase
import com.healthyorg.android.healthyapp.database.FavoriteWorkoutDatabase
import com.healthyorg.android.healthyapp.workoutClasses.Favorite_Workouts
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val DATABASE_NAME = "favorite-workout-database"

class FavoriteWorkoutRepository private constructor(context: Context) {

    private val database: FavoriteWorkoutDatabase = Room.databaseBuilder(
        context.applicationContext,
        FavoriteWorkoutDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val favoriteWorkoutDao = database.favoriteWorkoutDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllFavoriteWorkouts(): LiveData<List<Favorite_Workouts>> = favoriteWorkoutDao.getAllFavoriteWorkouts()
    fun insertFavoriteWorkout(workout: Favorite_Workouts){
        executor.execute{
            favoriteWorkoutDao.insertFavoriteWorkout(workout)
        }
    }
    fun insertAllFavoriteWorkouts(workouts: List<Favorite_Workouts>){
        executor.execute{
            for (item in workouts){
                favoriteWorkoutDao.insertFavoriteWorkout(item)
            }
        }
    }

    companion object {
        private var INSTANCE: FavoriteWorkoutRepository? = null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE = FavoriteWorkoutRepository(context)
            }
        }
        fun get(): FavoriteWorkoutRepository {
            return INSTANCE?:
            throw IllegalStateException("FavoriteWorkoutRepository must be initialized")
        }
    }
}