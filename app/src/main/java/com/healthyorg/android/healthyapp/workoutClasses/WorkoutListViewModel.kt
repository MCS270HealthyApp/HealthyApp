package com.healthyorg.android.healthyapp.workoutClasses

import androidx.lifecycle.ViewModel

class WorkoutListViewModel: ViewModel() {
    private val workoutRepository = WorkoutRepository.get()
    val workoutListLiveData = workoutRepository.getAllWorkouts()

    fun addWorkout(workout: Daily_Workout){
        workoutRepository.insertWorkout(workout)
    }
}