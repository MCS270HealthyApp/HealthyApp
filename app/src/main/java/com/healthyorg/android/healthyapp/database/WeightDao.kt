package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.weightClasses.Daily_Weight
import com.healthyorg.android.healthyapp.workoutClasses.Daily_Workout

@Dao
interface WeightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeight(vararg daily_weight: Daily_Weight)

    @Query("SELECT * FROM daily_weight")
    fun getAllWeights(): LiveData<List<Daily_Weight>>

    @Query("SELECT * FROM daily_weight WHERE date > :date")
    fun getAllWeightsAfter(date: Long?): List<Daily_Weight>
}