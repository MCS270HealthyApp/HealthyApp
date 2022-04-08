package com.healthyorg.android.healthyapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Goal(
    @PrimaryKey val title: String,
    @ColumnInfo var isChecked: Boolean = false
)