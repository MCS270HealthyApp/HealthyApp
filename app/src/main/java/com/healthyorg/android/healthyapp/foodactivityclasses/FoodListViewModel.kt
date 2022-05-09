package com.healthyorg.android.healthyapp.foodactivityclasses

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

private const val TAG = "FoodListViewModel"

class FoodListViewModel: ViewModel() {
    //food and favoriteFoodRepository initialized
    private val foodRepository = FoodRepository.get()
    private val favoriteFoodRepository = FavoriteFoodRepository.get()
    //Data from the repositories stored for use
    val foodListLiveData = foodRepository.getAllMeals()
    val favoriteFoodList = favoriteFoodRepository.getAllFavoriteMeals()

    //Don't question this too much. I hope it's self evident
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
        Meal("Pork Chop", 257.0),
        Meal("T-Bone", 580.0),
        Meal("Bagel", 252.0),
        Meal("Banana Bread", 91.0),
        Meal("Bread stick", 20.0),
        Meal("Brownie", 162.0),
        Meal("Croissant", 231.0),
        Meal("Cupcake", 131.0),
        Meal("Muffin", 210.0),
        Meal("Naan", 440.0),
        Meal("Sandwich Bread", 65.0),
        Meal("White Bread", 67.0),
        Meal("Whole Wheat Bread", 114.0)
    )

    /**
     * Calculate the daily calories for the last seven days and return a list of the values
     */
    fun calcDailyCals(meals: List<Meal>): List<Double>{
        Log.i(TAG, "calcDailyCals called with input ${meals.toString()}")
        val reverseMeals = meals.reversed()
        //List to be returned
        val dailyCalsList: ArrayList<Double> = ArrayList()
        //Instantiate dates the first being the one for comparison and the second being one to overwrite with a meal object's consumption date
        val mostRecentDate: Calendar = Calendar.getInstance()
        val mealDate: Calendar = Calendar.getInstance()
        //Used to store the lastMeal checked so there is not iteration overlap
        var latestMeal = 0
        /*Loop goes through the 7 day period required for the graph*/
        for (i in 0 until 7) {
            dailyCalsList.add(0.0)
            //The date is decremented by 24 hours to check if an item was eaten "after" the time/date
            mostRecentDate.add(Calendar.DATE, -1)
            Log.i(TAG, "LatestMeal: ${latestMeal} and meals: ${reverseMeals.size}")
            /*Loop through the meal list adding item calories to the daily count
            * if it was eaten on the specified date*/
            for (j in latestMeal until reverseMeals.size) {
                mealDate.time = reverseMeals[j].date
                //If the meal is within the appropriate timeframe it's position is saved as the latest meal
                //It's calories are also added to the day's sum
                if(mealDate.after(mostRecentDate)){
                    latestMeal = j
                    dailyCalsList[i] = dailyCalsList[i] + reverseMeals[j].food_cals
                } else{ //Since foods are sorted by date, any items farther in the list are past the date so the loop breaks
                    latestMeal++
                    break
                }
            }
        }
        Log.i(TAG, "DailyCalsList = ${dailyCalsList.toString()}")
        return dailyCalsList.reversed()
    }

    //FavoriteMeals are only added as single items
    /**
     * Adds a single favoriteMeal to the favoriteFoodRepository
     */
    fun addFavoriteMeal(food: FavoriteMeal){
        favoriteFoodRepository.insertFavoriteFood(food)
    }

    /**
     * Add one Meal object to the foodRepository
     */
    fun addMeal(food: Meal){
        foodRepository.insertFood(food)
    }

    /**
     * Add a list of Meal objects to the foodRepository
     */
    fun addAllMeals(foods: List<Meal>){
        foodRepository.insertAllFoods(foods)
    }

    /**
     * Delete a specified Meal from the foodRepository
     */
    fun deleteFood(food: Meal){
        foodRepository.deleteFood(food)
    }
}