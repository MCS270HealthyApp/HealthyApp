package com.healthyorg.android.healthyapp.MoodClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Daily_Mood (
    @ColumnInfo var feelings: String,
    @PrimaryKey var date: Date = Date())