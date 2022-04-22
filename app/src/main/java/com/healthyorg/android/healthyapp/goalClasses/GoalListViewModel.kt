package com.healthyorg.android.healthyapp.goalClasses

import androidx.lifecycle.ViewModel

class GoalListViewModel: ViewModel() {
    private val goalsRepository = GoalsRepository.get()
    val goalListLiveData = goalsRepository.getAllGoals()

    fun addGoal(goal: Goal){
        goalsRepository.insertGoal(goal)
    }

    fun deleteGoal(goal: Goal){
        goalsRepository.deleteGoal(goal)
    }
}