package com.healthyorg.android.healthyapp.database

import androidx.room.TypeConverter
import java.util.*

//Two type converters that convert the Date type into a format
//that our database system can actually store, since Room
//doesn't support it normally.
class SleepTypeConverters {

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