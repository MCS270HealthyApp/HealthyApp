package com.healthyorg.android.healthyapp.database

import android.content.Context
import androidx.room.*
import com.healthyorg.android.healthyapp.Daily_Weight
import com.healthyorg.android.healthyapp.database.WeightDao
import com.healthyorg.android.healthyapp.database.WeightTypeConverters

@Database(entities = [Daily_Weight::class], version = 1)
@TypeConverters(WeightTypeConverters::class)
abstract class WeightDatabase : RoomDatabase() {
    abstract fun weightDao(): WeightDao

    companion object{
        @Volatile
        private var INSTANCE: WeightDatabase? = null

        fun getDatabase(
            context: Context
        ): WeightDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeightDatabase::class.java,
                    "weight_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}