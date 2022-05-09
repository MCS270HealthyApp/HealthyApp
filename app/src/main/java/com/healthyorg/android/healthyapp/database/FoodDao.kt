package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.foodactivityclasses.Meal

//class for accessing database
@Dao
interface FoodDao {
    //inserts items into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(vararg food: Meal)

    //gets all items in database as livedata list
    @Query("SELECT * FROM Meal")
    fun getAllMeals(): LiveData<List<Meal>>

    //gets all objects in database after data as a regular list
    @Query("SELECT * FROM Meal WHERE date > :date")
    fun getAllMealsAfter(date: Long?): List<Meal>

    //deletes entry from database
    @Delete
    fun deleteFood(vararg food: Meal)
}