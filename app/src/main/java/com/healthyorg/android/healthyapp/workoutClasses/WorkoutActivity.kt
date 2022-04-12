package com.healthyorg.android.healthyapp.workoutClasses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.healthyorg.android.healthyapp.R
import com.healthyorg.android.healthyapp.database.WorkoutDatabase

class WorkoutActivity : AppCompatActivity() {
    private lateinit var workoutEntryButton: Button
    private lateinit var workoutEditText: EditText
    private lateinit var typeEditText: EditText
    private lateinit var calorieEditText: EditText

    private val workoutListViewModel: WorkoutListViewModel by lazy {
        ViewModelProviders.of(this).get(WorkoutListViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        workoutEntryButton = findViewById(R.id.workout_submit_button)
        workoutEditText = findViewById(R.id.workout_name)
        typeEditText = findViewById(R.id.workout_type)
        calorieEditText = findViewById(R.id.calorie_used)


        val currentFragment  = supportFragmentManager.findFragmentById(R.id.workout_fragment_container)

        workoutEntryButton.setOnClickListener{
            workoutListViewModel.addWorkout(Daily_Workout(workoutName = workoutEditText.text.toString(), workoutType = typeEditText.text.toString(), calorieBurned = calorieEditText.text.toString().toDouble()))
        }

        if(currentFragment == null){
            val fragment = WorkoutListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.workout_fragment_container, fragment).commit()
        }
        val db = Room.databaseBuilder(
            applicationContext,
            WorkoutDatabase::class.java, "database-name"
        ).build()
    }
}