package com.healthyorg.android.healthyapp.foodactivityclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class storing relevant information for a food item/meal.
 * Treated as an Entity for use with Room preservation
 */
@Entity
data class FavoriteMeal(
    @PrimaryKey var food_type: String,
    @ColumnInfo var food_cals: Double
    )
