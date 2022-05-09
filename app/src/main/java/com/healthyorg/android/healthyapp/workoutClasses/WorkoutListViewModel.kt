package com.healthyorg.android.healthyapp.workoutClasses

import androidx.lifecycle.ViewModel
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteWorkoutRepository

class WorkoutListViewModel: ViewModel() {
    private val workoutRepository = WorkoutRepository.get()
    private val favoriteWorkoutRepository = FavoriteWorkoutRepository.get()

    val workoutListLiveData = workoutRepository.getAllWorkouts()
    val favoriteWorkoutList = favoriteWorkoutRepository.getAllFavoriteWorkouts()

    val suggestedWorkoutList: List<Daily_Workout> = listOf(
        Daily_Workout("20 Situps", "Core", 10.0),
        Daily_Workout("20 Pushups", "Upper Body", 10.0),
        Daily_Workout("5 Pullups", "Upper Body", 15.0),
        Daily_Workout("30 minutes run", "Cardio", 300.0),
        Daily_Workout("8 Boxjumps", "Legs", 6.0),
        Daily_Workout("1 minute plank", "Core", 10.0),
        Daily_Workout("3x8 BB Benchpress", "Upper Body", 20.0),
        Daily_Workout("3x8 BB Back Squat", "Legs", 30.0),
        Daily_Workout("3x8 Lateral Pulldowns", "Upper Body", 20.0),
        Daily_Workout("3x8 Deadlifts", "Legs", 30.0),
        Daily_Workout("3x8 Bent Over Row", "Upper Body", 20.0),
        Daily_Workout("Throwing a 10kg rock out of earths orbit", "Upper Body", 149286.737),
        Daily_Workout("3x8 (per leg) Bulgarian Squats", "Legs", 30.0)
    )

    /**
     * Adds a single favoriteWorkout to the favoriteWorkoutRepository
     */
    fun addFavoriteWorkout(workout: Favorite_Workouts){
        favoriteWorkoutRepository.insertFavoriteWorkout(workout)
    }
    //add workout to workout repository
    fun addWorkout(workout: Daily_Workout){
        workoutRepository.insertWorkout(workout)
    }
    //add all workouts selected to workout repository
    fun addAllWorkouts(workouts: List<Daily_Workout>){
        workoutRepository.insertAllWorkouts(workouts)
    }
    //delete workout from workout repository
    fun deleteWorkout(workout: Daily_Workout){
        workoutRepository.deleteWorkout(workout)
    }
}