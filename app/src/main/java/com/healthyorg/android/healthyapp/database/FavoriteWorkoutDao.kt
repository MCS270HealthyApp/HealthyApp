package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteMeal
import com.healthyorg.android.healthyapp.workoutClasses.Favorite_Workouts

@Dao
interface FavoriteWorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteWorkout(vararg workout: Favorite_Workouts)

    @Query("SELECT * FROM Favorite_Workouts")
    fun getAllFavoriteWorkouts(): LiveData<List<Favorite_Workouts>>

    @Query("SELECT * FROM Favorite_Workouts")
    fun getAllFavoriteWorkoutsList(): List<Favorite_Workouts>

    @Delete
    fun deleteFavoriteWorkout(vararg workout: Favorite_Workouts)
}