package com.healthyorg.android.healthyapp

import androidx.lifecycle.ViewModel

class GoalListViewModel: ViewModel() {
    private val goalsRepository = GoalsRepository.get()
    val goalListLiveData = goalsRepository.getAllGoals()

    fun addGoal(goal: Goal){
        goalsRepository.insertGoal(goal)
    }
}