package com.healthyorg.android.healthyapp.database

import android.content.Context
import androidx.room.*
import com.healthyorg.android.healthyapp.foodactivityclasses.Meal

//Functions automatically implemented by Room using specified constraints
@Database(entities = [Meal::class], version = 1)
@TypeConverters(WeightTypeConverters::class)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    companion object{
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        //Fun to obtain and build database defined
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