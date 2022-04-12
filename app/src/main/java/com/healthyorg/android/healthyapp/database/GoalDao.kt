package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.goalClasses.Goal

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoal(vararg goal: Goal)


    @Query("SELECT * FROM Goal")
    fun getAllGoals(): LiveData<List<Goal>>
}