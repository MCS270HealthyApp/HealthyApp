package com.healthyorg.android.healthyapp

import android.app.Application
import com.healthyorg.android.healthyapp.foodactivityclasses.FoodRepository
import com.healthyorg.android.healthyapp.MoodClasses.MoodRepository
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteFoodRepository
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteWorkoutRepository
import com.healthyorg.android.healthyapp.goalClasses.GoalsRepository
import com.healthyorg.android.healthyapp.sleepClasses.SleepRepository
import com.healthyorg.android.healthyapp.weightClasses.WeightRepository
import com.healthyorg.android.healthyapp.workoutClasses.WorkoutRepository

class HealthyAppApplication: Application() {
    override fun onCreate(){
        super.onCreate()
        WeightRepository.initialize(this)
        GoalsRepository.initialize(this)
        FoodRepository.initialize(this)
        FavoriteFoodRepository.initialize(this)
        SleepRepository.initialize(this)
        MoodRepository.initialize(this)
        WorkoutRepository.initialize(this)
        FavoriteWorkoutRepository.initialize(this)
    }
}