package com.healthyorg.android.healthyapp.goalClasses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.healthyorg.android.healthyapp.MoodClasses.Daily_Mood
import com.healthyorg.android.healthyapp.database.GoalsDatabase
import java.util.concurrent.Executors

private const val DATABASE_NAME = "goals-database"

/**
 * Repository constructor
 */
class GoalsRepository private constructor(context: Context) {

    //Builds a database of type GoalsDatabase
    private val database: GoalsDatabase = Room.databaseBuilder(
        context.applicationContext,
        GoalsDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    private val GoalDao = database.GoalDao()
    private val executor = Executors.newSingleThreadExecutor()

    //Initializes a data access object for interacting with the databade
    fun getAllGoalsList(): List<Goal> = GoalDao.getAllGoalsList()
    fun getAllGoals(): LiveData<List<Goal>> = GoalDao.getAllGoals()

    fun insertGoal(goal: Goal){
        executor.execute{
            GoalDao.insertGoal(goal)
        }
    }

    fun deleteGoal(goal: Goal){
        executor.execute {
            GoalDao.deleteGoal(goal)
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