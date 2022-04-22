package com.healthyorg.android.healthyapp.workoutClasses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.WorkoutDatabase
import com.healthyorg.android.healthyapp.foodactivityclasses.Meal
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "workout-database"

class WorkoutRepository private constructor(context: Context) {

    private val database: WorkoutDatabase = Room.databaseBuilder(
        context.applicationContext,
        WorkoutDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    private val workoutDao = database.workoutDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllWorkouts(): LiveData<List<Daily_Workout>> = workoutDao.getAllWorkouts()
    fun insertWorkout(workout: Daily_Workout){
        executor.execute{
            workoutDao.insertWorkout(workout)
        }
    }

    fun deleteWorkout(workout: Daily_Workout){
        executor.execute{
            workoutDao.deleteWorkout(workout)
        }
    }
    fun insertAllWorkouts(workouts: List<Daily_Workout>){
        executor.execute{
            for (item in workouts){
                item.date = Date()
                workoutDao.insertWorkout(item)
                //The slight delay prevents the dates from being identical and overwriting other entries
                Thread.sleep(50)
            }
        }
    }

    companion object {
        private var INSTANCE: WorkoutRepository? = null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE = WorkoutRepository(context)
            }
        }
        fun get(): WorkoutRepository {
            return INSTANCE?:
            throw IllegalStateException("WorkoutRepository must be initialized")
        }
    }
}