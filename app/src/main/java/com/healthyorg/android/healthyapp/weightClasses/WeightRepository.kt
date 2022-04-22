package com.healthyorg.android.healthyapp.weightClasses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.WeightDatabase
import java.util.concurrent.Executors

private const val DATABASE_NAME = "weight-database"

class WeightRepository private constructor(context: Context) {

    private val database: WeightDatabase = Room.databaseBuilder(
        context.applicationContext,
        WeightDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val weightDao = database.weightDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllWeightsAfter(date: Long?): List<Daily_Weight> = weightDao.getAllWeightsAfter(date)
    fun getAllWeights(): LiveData<List<Daily_Weight>> = weightDao.getAllWeights()
    fun insertWeight(weight: Daily_Weight){
        executor.execute{
            weightDao.insertWeight(weight)
        }
    }

    companion object {
        private var INSTANCE: WeightRepository? = null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE = WeightRepository(context)
            }
        }
        fun get(): WeightRepository {
            return INSTANCE?:
            throw IllegalStateException("WeightRepository must be initialized")
        }
    }
}