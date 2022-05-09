package com.healthyorg.android.healthyapp.goalClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Data class storing relevant information for a goal.
 * Treated as an Entity for use with Room preservation
 */
@Entity
data class Goal(
    @PrimaryKey val title: String,
    @ColumnInfo var isChecked: Boolean = false
)