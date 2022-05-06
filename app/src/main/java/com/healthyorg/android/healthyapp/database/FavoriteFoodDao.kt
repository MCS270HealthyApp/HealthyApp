package com.healthyorg.android.healthyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteMeal

@Dao
interface FavoriteFoodDao {
    //Room automatically implements functions based off annotation

    //Insert function which overwrites on conflicts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteFood(vararg food: FavoriteMeal)

    //Select all FavoriteMeals from the dtabase
    @Query("SELECT * FROM FavoriteMeal")
    fun getAllFavoriteMeals(): LiveData<List<FavoriteMeal>>
}