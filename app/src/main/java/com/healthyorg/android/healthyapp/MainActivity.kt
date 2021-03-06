package com.healthyorg.android.healthyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.healthyorg.android.healthyapp.MoodClasses.MoodActivity
import com.healthyorg.android.healthyapp.focusTimer.FocusActivity
import com.healthyorg.android.healthyapp.foodactivityclasses.FoodActivity
import com.healthyorg.android.healthyapp.goalClasses.GoalsActivity
import com.healthyorg.android.healthyapp.sleepClasses.SleepActivity
import com.healthyorg.android.healthyapp.weightClasses.WeightActivity
import com.healthyorg.android.healthyapp.workoutClasses.WorkoutActivity
import notesClasses.NotesActivity

/*
Main page that links to all other pages
 */
class MainActivity : AppCompatActivity() {

    private lateinit var summaryButton: Button
    private lateinit var workoutButton: Button
    private lateinit var weightButton: Button
    private lateinit var foodButton: Button
    private lateinit var notesButton: Button
    private lateinit var goalsButton: Button
    private lateinit var sleepButton: Button
    private lateinit var quotesButton: Button
    private lateinit var moodButton: Button
    private lateinit var focusButton: Button
    private lateinit var settingsButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        summaryButton = findViewById(R.id.summary_page_button)
        workoutButton = findViewById(R.id.workout_page_button)
        weightButton = findViewById(R.id.weight_page_button)
        foodButton = findViewById(R.id.food_page_button)
        notesButton = findViewById(R.id.notes_page_button)
        goalsButton = findViewById(R.id.goals_button)
        sleepButton = findViewById(R.id.sleep_page_button)
        quotesButton = findViewById(R.id.quotes_page_button)
        moodButton = findViewById(R.id.mood_page_button)
        focusButton = findViewById(R.id.timer_button)
        settingsButton = findViewById(R.id.settings_button)

        summaryButton.setOnClickListener{
            val intent = Intent(this, Summary::class.java)
            startActivity(intent)
        }

        workoutButton.setOnClickListener{
            val intent = Intent(this, WorkoutActivity::class.java)
            startActivity(intent)
        }

        weightButton.setOnClickListener{
            val intent = Intent(this, WeightActivity::class.java)
            startActivity(intent)
        }

        foodButton.setOnClickListener{
            val intent = Intent(this, FoodActivity::class.java)
            startActivity(intent)
        }

        notesButton.setOnClickListener{
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
        }

        goalsButton.setOnClickListener {
            val intent = Intent(this, GoalsActivity::class.java)
            startActivity(intent)
        }

        sleepButton.setOnClickListener {
            val intent = Intent(this, SleepActivity::class.java)
            startActivity(intent)
        }

        quotesButton.setOnClickListener {
            val intent = Intent(this, QuotesActivity::class.java)
            startActivity(intent)
        }

        moodButton.setOnClickListener {
            val intent = Intent(this, MoodActivity::class.java)
            startActivity(intent)
        }
        focusButton.setOnClickListener {
            val intent = Intent(this, FocusActivity::class.java)
            startActivity(intent)
        }
        settingsButton.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }
    }
}