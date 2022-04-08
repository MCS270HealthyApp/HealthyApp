package com.healthyorg.android.healthyapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.WorkoutDatabase
import java.lang.IllegalStateException
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