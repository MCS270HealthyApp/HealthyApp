package com.healthyorg.android.healthyapp.foodactivityclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
/**
 * Data class storing relevant information for a food item/meal.
 * Treated as an Entity for use with Room preservation
 */
@Entity
data class Meal(
    @ColumnInfo var food_type: String,
    @ColumnInfo var food_cals: Double,
    @PrimaryKey var date: Date = Date())
