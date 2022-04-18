package com.healthyorg.android.healthyapp.foodactivityclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMeal(
    @PrimaryKey var food_type: String,
    @ColumnInfo var food_cals: Double
    )
