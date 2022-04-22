package com.healthyorg.android.healthyapp.workoutClasses

import androidx.lifecycle.ViewModel
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteWorkoutRepository

class WorkoutListViewModel: ViewModel() {
    private val workoutRepository = WorkoutRepository.get()
    private val favoriteWorkoutRepository = FavoriteWorkoutRepository.get()

    val workoutListLiveData = workoutRepository.getAllWorkouts()
    val favoriteWorkoutList = favoriteWorkoutRepository.getAllFavoriteWorkouts()


    fun addFavoriteWorkout(workout: Favorite_Workouts){
        favoriteWorkoutRepository.insertFavoriteWorkout(workout)
    }
    fun addWorkout(workout: Daily_Workout){
        workoutRepository.insertWorkout(workout)
    }
    fun addAllWorkouts(workouts: List<Daily_Workout>){
        workoutRepository.insertAllWorkouts(workouts)
    }
    fun deleteWorkout(workout: Daily_Workout){
        workoutRepository.deleteWorkout(workout)
    }
}