package com.healthyorg.android.healthyapp.foodactivityclasses

import com.healthyorg.android.healthyapp.database.WeightDatabase
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.Daily_Weight
import com.healthyorg.android.healthyapp.database.FoodDatabase
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val DATABASE_NAME = "food-database"

class FoodRepository private constructor(context: Context) {

    private val database: FoodDatabase = Room.databaseBuilder(
        context.applicationContext,
        FoodDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val foodDao = database.foodDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllMeals(): LiveData<List<Meal>> = foodDao.getAllMeals()
    fun insertFood(food: Meal){
        executor.execute{
            foodDao.insertFood(food)
        }
    }

    companion object {
        private var INSTANCE: FoodRepository? = null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE = FoodRepository(context)
            }
        }
        fun get(): FoodRepository {
            return INSTANCE?:
            throw IllegalStateException("FoodRepository must be initialized")
        }
    }
}