package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.foodactivityclasses.Meal

//Room automatically implements functions based on annotations
@Dao
interface FoodDao {
    //Insert a meal object into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(vararg food: Meal)

    //Select all meals from the database
    @Query("SELECT * FROM Meal")
    fun getAllMeals(): LiveData<List<Meal>>

    //Select a specific set of meals after the input date
    @Query("SELECT * FROM Meal WHERE date > :date")
    fun getAllMealsAfter(date: Long?): List<Meal>

    //Delete a specific meal
    @Delete
    fun deleteFood(vararg food: Meal)
}