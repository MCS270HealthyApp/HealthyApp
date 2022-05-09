package com.healthyorg.android.healthyapp.foodactivityclasses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.MoodClasses.Daily_Mood
import com.healthyorg.android.healthyapp.database.FoodDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "food-database"

/**
 * Repository constructor
 */
class FoodRepository private constructor(context: Context) {

    //Builds a database of type FoodDatabase
    private val database: FoodDatabase = Room.databaseBuilder(
        context.applicationContext,
        FoodDatabase::class.java,
        DATABASE_NAME
    ).build()

    //Initializes a data access object for interacting with the database
    private val foodDao = database.foodDao()
    private val executor = Executors.newSingleThreadExecutor()

    /*Functions for database additions an removals declared and defined*/
    fun getAllMealsAfter(date: Long?): List<Meal> = foodDao.getAllMealsAfter(date)
    fun getAllMeals(): LiveData<List<Meal>> = foodDao.getAllMeals()
    fun insertFood(food: Meal){
        executor.execute{
            foodDao.insertFood(food)
        }
    }
    fun deleteFood(food: Meal){
        executor.execute {
            foodDao.deleteFood(food)
        }
    }

    fun insertAllFoods(food: List<Meal>){
        executor.execute{
            for (item in food){
                item.date = Date()
                foodDao.insertFood(item)
                //The slight delay prevents the dates from being identical and overwriting other entries
                Thread.sleep(50)
            }
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