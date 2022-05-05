package com.healthyorg.android.healthyapp.MoodClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//Creating our mood class, which has a string, which is how someone is feeling,
//and a date, which will be the current date.
@Entity
data class Daily_Mood (
    @ColumnInfo var feelings: String,
    @PrimaryKey var date: Date = Date())