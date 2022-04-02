package com.healthyorg.android.healthyapp

import androidx.room.*

@Dao
interface WeightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeight(vararg daily_weight: Daily_Weight)

    @Query("SELECT * FROM daily_weight")
    fun loadAllWeights(): Array<Daily_Weight>
}