package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.Daily_Weight

@Dao
interface WeightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeight(vararg daily_weight: Daily_Weight)

    @Query("SELECT * FROM daily_weight")
    fun getAllWeights(): LiveData<List<Daily_Weight>>
}