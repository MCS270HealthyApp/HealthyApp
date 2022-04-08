package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.DailySleepMood
import com.healthyorg.android.healthyapp.Daily_Weight

@Dao
interface SleepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSleep(vararg dailysleepmood: DailySleepMood)

    @Query("SELECT * FROM dailysleepmood")
    fun getAllSleeps(): LiveData<List<DailySleepMood>>
}