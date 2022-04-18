package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteMeal

@Dao
interface FavoriteFoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteFood(vararg food: FavoriteMeal)


    @Query("SELECT * FROM Meal")
    fun getAllFavoriteMeals(): LiveData<List<FavoriteMeal>>
}