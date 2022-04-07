package com.healthyorg.android.healthyapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Goal(
    @ColumnInfo val title: String,
    @PrimaryKey var isChecked: Boolean = false
)