package com.healthyorg.android.healthyapp.workoutClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Daily_Workout(
    @ColumnInfo val workoutName: String,
    @ColumnInfo var workoutType: String,
    @ColumnInfo var calorieBurned: Double,
    @PrimaryKey var date: Date = Date()
)