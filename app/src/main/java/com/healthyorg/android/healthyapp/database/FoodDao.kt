package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.foodactivityclasses.Meal
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(vararg food: Meal)


    @Query("SELECT * FROM Meal")
    fun getAllMeals(): LiveData<List<Meal>>

    @Delete
    fun deleteFood(vararg food: Meal)
}