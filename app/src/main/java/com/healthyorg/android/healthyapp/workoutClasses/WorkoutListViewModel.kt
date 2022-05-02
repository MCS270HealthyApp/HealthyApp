package com.healthyorg.android.healthyapp.workoutClasses

import androidx.lifecycle.ViewModel
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteWorkoutRepository

class WorkoutListViewModel: ViewModel() {
    private val workoutRepository = WorkoutRepository.get()
    private val favoriteWorkoutRepository = FavoriteWorkoutRepository.get()

    val workoutListLiveData = workoutRepository.getAllWorkouts()
    val favoriteWorkoutList = favoriteWorkoutRepository.getAllFavoriteWorkouts()

    val suggestedWorkoutList: List<Daily_Workout> = listOf(
        Daily_Workout("20 Situps", "Core", 20.0),
        Daily_Workout("20 Pushups", "Upper Body", 20.0)
    )


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