package com.healthyorg.android.healthyapp.foodactivityclasses

import androidx.lifecycle.ViewModel

class FoodListViewModel: ViewModel() {
    private val foodRepository = FoodRepository.get()
    val foodListLiveData = foodRepository.getAllMeals()

    fun addMeal(food: Meal){
        foodRepository.insertFood(food)
    }
}