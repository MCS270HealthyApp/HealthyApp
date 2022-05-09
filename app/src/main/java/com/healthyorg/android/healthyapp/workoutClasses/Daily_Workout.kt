package com.healthyorg.android.healthyapp.workoutClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
/**
 * Data class storing relevant information for a workout.
 * Treated as an Entity for use with Room preservation
 */
@Entity
data class Daily_Workout(
    @ColumnInfo val workoutName: String,
    @ColumnInfo var workoutType: String,
    @ColumnInfo var calorieBurned: Double,
    @PrimaryKey var date: Date = Date()
)