package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood

@Dao
interface SleepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSleep(vararg dailysleepmood: DailySleepMood)

    @Query("SELECT * FROM dailysleepmood")
    fun getAllSleeps(): LiveData<List<DailySleepMood>>
}