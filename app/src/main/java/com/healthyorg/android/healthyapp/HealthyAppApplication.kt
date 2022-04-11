package com.healthyorg.android.healthyapp

import android.app.Application
import com.healthyorg.android.healthyapp.foodactivityclasses.FoodRepository

class HealthyAppApplication: Application() {
    override fun onCreate(){
        super.onCreate()
        WeightRepository.initialize(this)
        GoalsRepository.initialize(this)
        FoodRepository.initialize(this)
        SleepRepository.initialize(this)
        MoodRepository.initialize(this)
        WorkoutRepository.initialize(this)
    }
}