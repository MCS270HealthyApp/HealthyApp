package com.healthyorg.android.healthyapp.sleepClasses

import com.healthyorg.android.healthyapp.database.SleepDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.healthyorg.android.healthyapp.R

//The main activity for our sleep page
class SleepActivity : AppCompatActivity() {
    private lateinit var sleepEntryButton: Button
    private lateinit var sleepEditText: EditText

    private val sleepListViewModel: SleepListViewModel by lazy {
        ViewModelProvider(this).get(SleepListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep)

        //Initializing the edit text where the user can enter how many hours
        //they slept last night, and the button to actually add it to the sleep database
        sleepEntryButton = findViewById(R.id.sleep_enter_button)
        sleepEditText = findViewById(R.id.sleep_edit_text)

        val currentFragment  = supportFragmentManager.findFragmentById(R.id.sleep_fragment_container)

        //The listener for the entry button, which will add a sleep object to the database,
        //using the number in the edit text and the current date
        sleepEntryButton.setOnClickListener{
            sleepListViewModel.addSleep(DailySleepMood(hours = sleepEditText.text.toString().toDouble()))
        }

        //Initializing our current fragment
        if(currentFragment == null){
            val fragment = SleepListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.sleep_fragment_container, fragment).commit()
        }
        val db = Room.databaseBuilder(
            applicationContext,
            SleepDatabase::class.java, "database-name"
        ).build()
    }
}