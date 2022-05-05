package com.healthyorg.android.healthyapp.MoodClasses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.MoodDatabase
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood
import java.util.concurrent.Executors

private const val DATABASE_NAME = "mood-database"

//This repository class defines the pattern we use to fetch and store
//data in our mood database
class MoodRepository private constructor(context: Context) {

    //Actually creating a concrete mood database
    private val database: MoodDatabase = Room.databaseBuilder(
        context.applicationContext,
        MoodDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val moodDao = database.moodDao()
    private val executor = Executors.newSingleThreadExecutor()

    //Functions that further implement the ability to find, insert, and delete mood objects
    //from the database by actually calling our Dao class
    fun getAllMoodsAfter(date: Long?): List<Daily_Mood> = moodDao.getAllMoodsAfter(date)
    fun getAllMoods(): LiveData<List<Daily_Mood>> = moodDao.getAllMoods()
    fun insertMood(mood: Daily_Mood){
        executor.execute{
            moodDao.insertMood(mood)
        }
    }

    fun deleteMood(mood: Daily_Mood){
        executor.execute {
            moodDao.deleteMood(mood)
        }
    }

    //Actually initializing our repository. We should only ever have one at any given time.
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