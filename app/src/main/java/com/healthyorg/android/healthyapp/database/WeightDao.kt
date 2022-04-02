package com.healthyorg.android.healthyapp.database

import androidx.room.*
import com.healthyorg.android.healthyapp.Daily_Weight

@Dao
interface WeightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeight(vararg daily_weight: Daily_Weight)

    @Query("SELECT * FROM daily_weight")
    fun getAllWeights(): Array<Daily_Weight>
}