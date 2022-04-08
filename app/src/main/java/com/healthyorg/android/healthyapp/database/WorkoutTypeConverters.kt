package com.healthyorg.android.healthyapp.database

import androidx.room.TypeConverter
import java.util.*

class WorkoutTypeConverters {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch:Long?): Date?{
        return millisSinceEpoch?.let{
            Date(it)
        }
    }
}