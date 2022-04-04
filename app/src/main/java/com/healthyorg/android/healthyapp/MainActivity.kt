package com.healthyorg.android.healthyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var summaryButton: Button
    private lateinit var workoutButton: Button
    private lateinit var weightButton: Button
    private lateinit var foodButton: Button
    private lateinit var notesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        summaryButton = findViewById(R.id.summary_page_button)
        workoutButton = findViewById(R.id.workout_page_button)
        weightButton = findViewById(R.id.weight_page_button)
        foodButton = findViewById(R.id.food_page_button)
        notesButton = findViewById(R.id.notes_page_button)

        summaryButton.setOnClickListener{
            //TODO: goto summary page
        }

        workoutButton.setOnClickListener{
            //TODO: goto workout page
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
            //TODO: goto notes page
        }
    }
}