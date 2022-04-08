package com.healthyorg.android.healthyapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DailySleepMood (
    @ColumnInfo var hours: Double,
    @PrimaryKey var date: Date = Date())