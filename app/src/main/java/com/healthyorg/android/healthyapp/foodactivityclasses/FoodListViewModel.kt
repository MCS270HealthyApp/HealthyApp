package com.healthyorg.android.healthyapp.foodactivityclasses

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.DateValidatorPointForward
import java.util.*

private const val TAG = "FoodListViewModel"

class FoodListViewModel: ViewModel() {
    private val foodRepository = FoodRepository.get()
    private val favoriteFoodRepository = FavoriteFoodRepository.get()
    val foodListLiveData = foodRepository.getAllMeals()
    val favoriteFoodList = favoriteFoodRepository.getAllFavoriteMeals()

    val genericFoodSelectionList: List<Meal> = listOf(
        Meal("Apple", 95.0),
        Meal("Banana", 111.0),
        Meal("Blackberries", 62.0),
        Meal("Cherries", 80.0),
        Meal("Grapes", 104.0),
        Meal("Mango", 202.0),
        Meal("Orange", 62.0),
        Meal("Pineapple", 453.0),
        Meal("Pomegranate", 234.0),
        Meal("Strawberries", 49.0),
        Meal("Watermelon", 86.0),
        Meal("Steak", 407.0),
        Meal("Chicken Breast", 344.0),
        Meal("Chicken Drumstick", 131.0),
        Meal("Filet Mignon", 278.0),
        Meal("Ham", 236.0),
        Meal("Prok Chop", 257.0),
        Meal("T-Bone", 580.0),
        Meal("Bagel", 252.0),
        Meal("Banana Bread", 91.0),
        Meal("Breadstick", 20.0),
        Meal("Brownie", 162.0),
        Meal("Croissant", 231.0),
        Meal("Cupcake", 131.0),
        Meal("Muffin", 210.0),
        Meal("Naan", 440.0),
        Meal("Sandwich Bread", 65.0),
        Meal("White Bread", 67.0),
        Meal("Whole Wheat Bread", 114.0)
    )

    fun calcDailyCals(meals: List<Meal>): List<Double>{
        //List to be returned
        var dailyCalsList: MutableList<Double> = Collections.nCopies(30, 0.0)
        //Instantiate dates the first being the one for comparison and the second being one to overwrite with a meal object's consumption date
        var mostRecentDate: Calendar = Calendar.getInstance()
        var mealDate: Calendar = Calendar.getInstance()
        var latestMeal = 0
        for (i in 0 until 30) {
            mostRecentDate.add(Calendar.DATE, -1)
            for (j in latestMeal until meals.size) {
                mealDate.time = meals[j].date
                if(mealDate.after(mostRecentDate)){
                    latestMeal = j
                    dailyCalsList[30-i] += meals[i].food_cals
                } else{
                    latestMeal++
                    break
                }
            }
        }
        return dailyCalsList
    }

    fun addFavoriteMeal(food: FavoriteMeal){
        favoriteFoodRepository.insertFavoriteFood(food)
    }

    fun addMeal(food: Meal){
        foodRepository.insertFood(food)
    }
    fun addAllMeals(foods: List<Meal>){
        foodRepository.insertAllFoods(foods)
    }

    fun deleteFood(food: Meal){
        foodRepository.deleteFood(food)
    }
}