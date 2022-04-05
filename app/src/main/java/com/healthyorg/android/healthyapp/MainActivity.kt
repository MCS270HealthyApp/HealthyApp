package com.healthyorg.android.healthyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var summaryButton: Button
    private lateinit var workoutButton: Button
    private lateinit var weightButton: Button
    private lateinit var foodButton: Button
    private lateinit var notesButton: Button
    private lateinit var sleepButton: Button
    private lateinit var quotesButton: Button
    private lateinit var moodButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        summaryButton = findViewById(R.id.summary_page_button)
        workoutButton = findViewById(R.id.workout_page_button)
        weightButton = findViewById(R.id.weight_page_button)
        foodButton = findViewById(R.id.food_page_button)
        notesButton = findViewById(R.id.notes_page_button)
        sleepButton = findViewById(R.id.sleep_page_button)
        quotesButton = findViewById(R.id.quotes_page_button)
        moodButton = findViewById(R.id.mood_page_button)

        summaryButton.setOnClickListener{
            //TODO: goto summary page
        }

        workoutButton.setOnClickListener{
            //TODO: goto workout page
        }

        weightButton.setOnClickListener{
            //TODO: goto weight page
        }

        foodButton.setOnClickListener{
            //TODO: goto food page
        }

        notesButton.setOnClickListener{
            //TODO: goto notes page
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
    }
}