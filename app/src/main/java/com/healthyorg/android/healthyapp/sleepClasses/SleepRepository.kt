package com.healthyorg.android.healthyapp.sleepClasses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.SleepDatabase
import java.util.concurrent.Executors

private const val DATABASE_NAME = "sleep-database"

//This repository class defines the pattern we use to fetch and store
//data in our sleep database
class SleepRepository private constructor(context: Context) {

    //Actually creating a concrete sleep database
    private val database: SleepDatabase = Room.databaseBuilder(
        context.applicationContext,
        SleepDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val sleepDao = database.sleepDao()
    private val executor = Executors.newSingleThreadExecutor()

    //Four functions that further implement the ability to find, insert, and delete sleep objects
    //from the database by actually calling our Dao class
    fun getAllSleepsAfter(date: Long?): List<DailySleepMood> = sleepDao.getAllSleepsAfter(date)
    fun getAllSleeps(): LiveData<List<DailySleepMood>> = sleepDao.getAllSleeps()
    fun insertSleep(sleep: DailySleepMood){
        executor.execute{
            sleepDao.insertSleep(sleep)
        }
    }

    fun deleteSleep(sleep: DailySleepMood){
        executor.execute {
            sleepDao.deleteSleep(sleep)
        }
    }

    //Actually initializing our repository. We should only ever have one at any given time.
    companion object {
        private var INSTANCE: SleepRepository? = null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE = SleepRepository(context)
            }
        }
        fun get(): SleepRepository {
            return INSTANCE?:
            throw IllegalStateException("SleepRepository must be initialized")
        }
    }
}