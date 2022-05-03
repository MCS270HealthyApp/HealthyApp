package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.foodactivityclasses.Meal
import com.healthyorg.android.healthyapp.goalClasses.Goal
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoal(vararg goal: Goal)


    @Query("SELECT * FROM Goal")
    fun getAllGoals(): LiveData<List<Goal>>

    @Query("SELECT * FROM Goal")
    fun getAllGoalsList(): List<Goal>

    @Delete
    fun deleteGoal(vararg goal: Goal)
}