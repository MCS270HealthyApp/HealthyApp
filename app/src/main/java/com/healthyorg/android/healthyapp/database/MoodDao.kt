package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.MoodClasses.Daily_Mood
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood

@Dao
interface MoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMood(vararg daily_mood: Daily_Mood)

    @Query("SELECT * FROM daily_mood")
    fun getAllMoods(): LiveData<List<Daily_Mood>>

    @Delete
    fun deleteMood(vararg daily_mood: Daily_Mood)
}