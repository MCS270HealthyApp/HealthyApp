package com.healthyorg.android.healthyapp.database

import android.content.Context
import androidx.room.*
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteMeal

@Database(entities = [FavoriteMeal::class], version = 1)
@TypeConverters(WeightTypeConverters::class)
abstract class FavoriteFoodDatabase : RoomDatabase() {
    abstract fun favoriteFoodDao(): FavoriteFoodDao

    companion object{
        @Volatile
        private var INSTANCE: FavoriteFoodDatabase? = null

        fun getDatabase(
            context: Context
        ): FavoriteFoodDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteFoodDatabase::class.java,
                    "favorite-food-database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}