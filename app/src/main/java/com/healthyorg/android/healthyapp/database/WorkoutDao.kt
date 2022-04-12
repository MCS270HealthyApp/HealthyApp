package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.workoutClasses.Daily_Workout

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkout(vararg daily_workout: Daily_Workout)

    @Query("SELECT * FROM daily_workout")
    fun getAllWorkouts(): LiveData<List<Daily_Workout>>
}