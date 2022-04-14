package com.healthyorg.android.healthyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthyorg.android.healthyapp.MoodClasses.MoodRepository
import com.healthyorg.android.healthyapp.foodactivityclasses.FoodRepository
import com.healthyorg.android.healthyapp.sleepClasses.SleepRepository
import com.healthyorg.android.healthyapp.workoutClasses.WorkoutRepository

class Summary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val workoutData = WorkoutRepository.get().getAllWorkouts()
        val foodData = FoodRepository.get().getAllMeals()
        val sleepData = SleepRepository.get().getAllSleeps()
        val moodData = MoodRepository.get().getAllMoods()


    }
}