package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.MoodClasses.Daily_Mood
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood
import com.healthyorg.android.healthyapp.workoutClasses.Daily_Workout

@Dao
interface MoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMood(vararg daily_mood: Daily_Mood)

    @Query("SELECT * FROM daily_mood")
    fun getAllMoods(): LiveData<List<Daily_Mood>>

    @Query("SELECT * FROM daily_mood WHERE date > :date")
    fun getAllMoodsAfter(date: Long?): List<Daily_Mood>
}