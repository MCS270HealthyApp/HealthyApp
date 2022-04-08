package com.healthyorg.android.healthyapp.database

import android.content.Context
import androidx.room.*
import com.healthyorg.android.healthyapp.Daily_Weight
import com.healthyorg.android.healthyapp.database.WeightDao
import com.healthyorg.android.healthyapp.database.WeightTypeConverters
import com.healthyorg.android.healthyapp.foodactivityclasses.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(WeightTypeConverters::class)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    companion object{
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(
            context: Context
        ): FoodDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "food-database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}