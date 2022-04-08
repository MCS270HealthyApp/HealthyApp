package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.Daily_Mood
import com.healthyorg.android.healthyapp.Daily_Weight

@Dao
interface MoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMood(vararg daily_mood: Daily_Mood)

    @Query("SELECT * FROM daily_mood")
    fun getAllMoods(): LiveData<List<Daily_Mood>>
}