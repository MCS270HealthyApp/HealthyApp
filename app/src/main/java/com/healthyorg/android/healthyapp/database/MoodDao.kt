package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.MoodClasses.Daily_Mood

//The dao file for our mood database. We initialize functions to
//insert, find, and delete mood objects from the database
@Dao
interface MoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMood(vararg daily_mood: Daily_Mood)

    @Query("SELECT * FROM daily_mood")
    fun getAllMoods(): LiveData<List<Daily_Mood>>

    @Query("SELECT * FROM daily_mood WHERE date > :date")
    fun getAllMoodsAfter(date: Long?): List<Daily_Mood>

    @Delete
    fun deleteMood(vararg daily_mood: Daily_Mood)
}