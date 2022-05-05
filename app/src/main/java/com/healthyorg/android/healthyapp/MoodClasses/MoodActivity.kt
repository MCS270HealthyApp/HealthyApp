package com.healthyorg.android.healthyapp.MoodClasses

import com.healthyorg.android.healthyapp.database.MoodDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.healthyorg.android.healthyapp.R

class MoodActivity : AppCompatActivity() {
    private lateinit var happyButton: Button
    private lateinit var mehButton: Button
    private lateinit var badButton: Button


    //Setting up the view model
    private val moodListViewModel: MoodListViewModel by lazy {
        ViewModelProvider(this).get(MoodListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)

        //Initializing the three buttons that the user can press
        happyButton = findViewById(R.id.mood_happy_button)
        mehButton = findViewById(R.id.mood_meh_button)
        badButton = findViewById(R.id.mood_bad_button)

        val currentFragment  = supportFragmentManager.findFragmentById(R.id.mood_fragment_container)

        //Listener for the happy button, which adds a new mood to the database
        happyButton.setOnClickListener{
            moodListViewModel.addMood(Daily_Mood(feelings = "Feeling good!"))
        }

        //Listener for the meh button, which adds a new mood to the database
        mehButton.setOnClickListener{
            moodListViewModel.addMood(Daily_Mood(feelings = "Feeling okay."))
        }

        //Listener for the bad button, which also adds a new mood to the database
        badButton.setOnClickListener{
            moodListViewModel.addMood(Daily_Mood(feelings = "Feeling bad."))
        }

        //Initializing our fragments
        if(currentFragment == null){
            val fragment = MoodListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.mood_fragment_container, fragment).commit()
        }
        val db = Room.databaseBuilder(
            applicationContext,
            MoodDatabase::class.java, "database-name"
        ).build()
    }
}