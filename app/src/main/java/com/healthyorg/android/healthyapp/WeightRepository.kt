package com.healthyorg.android.healthyapp

import com.healthyorg.android.healthyapp.database.WeightDatabase
import android.content.Context
import androidx.room.Room
import java.lang.IllegalStateException

private const val DATABASE_NAME = "weight-database"

class WeightRepository private constructor(context: Context) {

    private val database: WeightDatabase = Room.databaseBuilder(
        context.applicationContext,
        WeightDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val weightDao = database.weightDao()

    fun getAllWeights(): Array<Daily_Weight> = weightDao.getAllWeights()

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