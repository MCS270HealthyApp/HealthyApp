package com.healthyorg.android.healthyapp.MoodClasses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.MoodDatabase
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood
import java.util.concurrent.Executors

private const val DATABASE_NAME = "mood-database"

class MoodRepository private constructor(context: Context) {

    private val database: MoodDatabase = Room.databaseBuilder(
        context.applicationContext,
        MoodDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val moodDao = database.moodDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllMoodsAfter(date: Long?): List<Daily_Mood> = moodDao.getAllMoodsAfter(date)
    fun getAllMoods(): LiveData<List<Daily_Mood>> = moodDao.getAllMoods()
    fun insertMood(mood: Daily_Mood){
        executor.execute{
            moodDao.insertMood(mood)
        }
    }

    companion object {
        private var INSTANCE: MoodRepository? = null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE = MoodRepository(context)
            }
        }
        fun get(): MoodRepository {
            return INSTANCE?:
            throw IllegalStateException("MoodRepository must be initialized")
        }
    }
}