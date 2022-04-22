package com.healthyorg.android.healthyapp.sleepClasses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.SleepDatabase
import java.util.concurrent.Executors

private const val DATABASE_NAME = "sleep-database"

class SleepRepository private constructor(context: Context) {

    private val database: SleepDatabase = Room.databaseBuilder(
        context.applicationContext,
        SleepDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val sleepDao = database.sleepDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllSleepsAfter(date: Long?): List<DailySleepMood> = sleepDao.getAllSleepsAfter(date)
    fun getAllSleeps(): LiveData<List<DailySleepMood>> = sleepDao.getAllSleeps()
    fun insertSleep(sleep: DailySleepMood){
        executor.execute{
            sleepDao.insertSleep(sleep)
        }
    }

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