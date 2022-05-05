package com.healthyorg.android.healthyapp.sleepClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//Creating our Sleep class (ignore the weird name), which consists of a double,
//which is how many hours the user slept last night, and a date, which will be the current date.
@Entity
data class DailySleepMood (
    @ColumnInfo var hours: Double,
    @PrimaryKey var date: Date = Date())