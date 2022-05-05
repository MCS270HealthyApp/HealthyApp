package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood

//The dao file for our sleep database. We initialize functions to
//insert, find, and delete sleep objects from the database
@Dao
interface SleepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSleep(vararg dailysleepmood: DailySleepMood)

    @Query("SELECT * FROM dailysleepmood")
    fun getAllSleeps(): LiveData<List<DailySleepMood>>

    @Query("SELECT * FROM dailysleepmood WHERE date > :date")
    fun getAllSleepsAfter(date: Long?): List<DailySleepMood>

    @Delete
    fun deleteSleep(vararg dailysleepmood: DailySleepMood)
}