package com.healthyorg.android.healthyapp

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.healthyorg.android.healthyapp.MoodClasses.MoodRepository
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteFoodRepository
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteWorkoutRepository
import com.healthyorg.android.healthyapp.foodactivityclasses.FoodRepository
import com.healthyorg.android.healthyapp.goalClasses.GoalsRepository
import com.healthyorg.android.healthyapp.sleepClasses.SleepRepository
import com.healthyorg.android.healthyapp.weightClasses.WeightRepository
import com.healthyorg.android.healthyapp.workoutClasses.WorkoutRepository


class Settings : AppCompatActivity() {
    private lateinit var toggleNight: Button
    private lateinit var resetData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toggleNight = findViewById(R.id.swap_themes_button)
        resetData = findViewById(R.id.reset_data_button)

        toggleNight.setOnClickListener {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        resetData.setOnClickListener {
            val alertDialog: AlertDialog? = this.let {
                val builder = AlertDialog.Builder(it)
                builder.setMessage(R.string.delete_date_dialog)
                builder.apply {
                    setPositiveButton(R.string.confirm_delete
                    ) { dialog, id ->
                        deleteAllData()
                    }
                    setNegativeButton(R.string.cancel
                    ) { dialog, id ->
                        dialog.cancel();
                    }
                }
                builder.create()
            }
            alertDialog?.show()
        }
    }

    private fun deleteAllData() {
        Thread(Runnable {
            val workoutRep = WorkoutRepository.get()
            for (i in workoutRep.getAllWorkoutsAfter(0)) {
                workoutRep.deleteWorkout(i)
            }

            val weightRep = WeightRepository.get()
            for (i in weightRep.getAllWeightsAfter(0)) {
                weightRep.deleteWeight(i)
            }

            val sleepRep = SleepRepository.get()
            for (i in sleepRep.getAllSleepsAfter(0)) {
                sleepRep.deleteSleep(i)
            }

            val moodRep = MoodRepository.get()
            for (i in moodRep.getAllMoodsAfter(0)) {
                moodRep.deleteMood(i)
            }

            val goalRep = GoalsRepository.get()
            for (i in goalRep.getAllGoalsList()) {
                goalRep.deleteGoal(i)
            }

            val foodRep = FoodRepository.get()
            for (i in foodRep.getAllMealsAfter(0)) {
                foodRep.deleteFood(i)
            }

            val favFoodRep = FavoriteFoodRepository.get()
            for (i in favFoodRep.getAllFavoriteMealsList()) {
                favFoodRep.deleteFavoriteMeal(i)
            }

            val favWorkoutRep = FavoriteWorkoutRepository.get()
            for (i in favWorkoutRep.getAllFavoriteWorkoutsList()) {
                favWorkoutRep.deleteFavoriteWorkout(i)
            }
        }).start()
    }
}