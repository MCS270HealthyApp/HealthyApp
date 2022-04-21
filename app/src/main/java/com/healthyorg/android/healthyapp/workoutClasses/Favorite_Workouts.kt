package com.healthyorg.android.healthyapp.workoutClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite_Workouts(
    @PrimaryKey val workoutName: String,
    @ColumnInfo var workoutType: String,
    @ColumnInfo var calorieBurned: Double
)