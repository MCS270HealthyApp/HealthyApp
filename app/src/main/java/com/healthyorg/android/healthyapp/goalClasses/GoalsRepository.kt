package com.healthyorg.android.healthyapp.goalClasses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.GoalsDatabase
import java.util.concurrent.Executors

private const val DATABASE_NAME = "goals-database"

class GoalsRepository private constructor(context: Context) {

    private val database: GoalsDatabase = Room.databaseBuilder(
        context.applicationContext,
        GoalsDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    private val GoalDao = database.GoalDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllGoals(): LiveData<List<Goal>> = GoalDao.getAllGoals()

    fun insertGoal(goal: Goal){
        executor.execute{
            GoalDao.insertGoal(goal)
        }
    }


    companion object {
        private var INSTANCE: GoalsRepository? = null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE = GoalsRepository(context)
            }
        }
        fun get(): GoalsRepository {
            return INSTANCE?:
            throw IllegalStateException("Goals Repository must be initialized")
        }
    }
}