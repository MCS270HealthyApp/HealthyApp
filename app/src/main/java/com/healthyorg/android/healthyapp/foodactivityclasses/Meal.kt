package com.healthyorg.android.healthyapp.foodactivityclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Meal(
    @ColumnInfo var food_type: String,
    @ColumnInfo var food_cals: Double,
    @PrimaryKey var date: Date = Date())
