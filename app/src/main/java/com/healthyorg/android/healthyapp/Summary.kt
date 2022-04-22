package com.healthyorg.android.healthyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import com.healthyorg.android.healthyapp.MoodClasses.Daily_Mood
import com.healthyorg.android.healthyapp.MoodClasses.MoodRepository
import com.healthyorg.android.healthyapp.foodactivityclasses.FoodRepository
import com.healthyorg.android.healthyapp.foodactivityclasses.Meal
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood
import com.healthyorg.android.healthyapp.sleepClasses.SleepRepository
import com.healthyorg.android.healthyapp.workoutClasses.Daily_Workout
import com.healthyorg.android.healthyapp.workoutClasses.WorkoutRepository
import kotlinx.android.synthetic.main.activity_summary.*
import java.lang.Math.round
import java.util.*
import kotlin.math.roundToInt

private const val TAG = "SummaryActivity"

class Summary : AppCompatActivity() {
    private lateinit var avg_burned_txt: TextView
    private lateinit var avg_cals_txt: TextView
    private lateinit var avg_sleep_txt: TextView
    private lateinit var avg_mood_txt: TextView

    private var workoutData: List<Daily_Workout> = emptyList()
    private var foodData: List<Meal> = emptyList()
    private var sleepData: List<DailySleepMood> = emptyList()
    private var moodData: List<Daily_Mood> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        avg_burned_txt = findViewById(R.id.avg_workout)
        avg_cals_txt = findViewById(R.id.avg_cals)
        avg_sleep_txt = findViewById(R.id.avg_sleep)
        avg_mood_txt = findViewById(R.id.avg_mood)

        Thread(Runnable {
            workoutData = WorkoutRepository.get().getAllWorkoutsAfter(Date().time - 604800000)
            moodData = MoodRepository.get().getAllMoodsAfter(Date().time - 604800000)
            sleepData = SleepRepository.get().getAllSleepsAfter(Date().time - 604800000)
            foodData = FoodRepository.get().getAllMealsAfter(Date().time - 604800000)

            runOnUiThread(Runnable {
                updateValues()
            })
        }).start()

    }

    private fun updateValues() {
        var avgCalsBurned = 0.0
        for (workout in workoutData) {
            avgCalsBurned += workout.calorieBurned
        }
        avgCalsBurned /= 7
        avg_burned_txt.setText(resources.getString(R.string.avg_burned_cals, avgCalsBurned.toString()))

        var avgCalsEaten = 0.0
        for (meal in foodData) {
            avgCalsEaten += meal.food_cals
        }
        avgCalsEaten /= 7
        avg_cals_txt.setText(resources.getString(R.string.avg_cals_eaten, avgCalsEaten.toString()))

        var avgSleep = 0.0
        for (sleep in sleepData) {
            avgSleep += sleep.hours
        }
        avgSleep /= 7
        avg_sleep_txt.setText(resources.getString(R.string.avg_time_slept, avgSleep.toString()))

        var avgMood = 0.0
        var avgMoodStr: String
        if (moodData.size != 0) {
            for (mood in moodData) {
                if (mood.feelings == "Feeling good!")
                    avgMood += 3
                else if (mood.feelings == "Feeling okay.")
                    avgMood += 2
                else if (mood.feelings == "Feeling bad.")
                    avgMood += 1
            }
            avgMood /= moodData.size
        }
        avgMoodStr = "(you haven't recorded your mood!)"
        when {
            avgMood.roundToInt() == 1 -> avgMoodStr = "feeling bad."
            avgMood.roundToInt() == 2 -> avgMoodStr = "feeling okay."
            avgMood.roundToInt() == 3 -> avgMoodStr = "feeling good."
        }
        avg_mood_txt.setText(resources.getString(R.string.avg_mood, avgMoodStr.toString()))
    }
}