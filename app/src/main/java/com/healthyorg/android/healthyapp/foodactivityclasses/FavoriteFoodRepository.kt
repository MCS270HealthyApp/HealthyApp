package com.healthyorg.android.healthyapp.foodactivityclasses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.FavoriteFoodDatabase
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val DATABASE_NAME = "favorite-food-database"

/*
 * Repository class and constructor declaration for favorite foods
 */
class FavoriteFoodRepository private constructor(context: Context) {

    //Database intialized and built
    private val database: FavoriteFoodDatabase = Room.databaseBuilder(
        context.applicationContext,
        FavoriteFoodDatabase::class.java,
        DATABASE_NAME
    ).build()

    //Data access object and executor initialized
    private val favoriteFoodDao = database.favoriteFoodDao()
    private val executor = Executors.newSingleThreadExecutor()

    //Data insert functions defined
    fun getAllFavoriteMeals(): LiveData<List<FavoriteMeal>> = favoriteFoodDao.getAllFavoriteMeals()
    fun insertFavoriteFood(food: FavoriteMeal){
        executor.execute{
            favoriteFoodDao.insertFavoriteFood(food)
        }
    }

    companion object {
        private var INSTANCE: FavoriteFoodRepository? = null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE = FavoriteFoodRepository(context)
            }
        }
        fun get(): FavoriteFoodRepository {
            return INSTANCE?:
            throw IllegalStateException("FavoriteFoodRepository must be initialized")
        }
    }
}