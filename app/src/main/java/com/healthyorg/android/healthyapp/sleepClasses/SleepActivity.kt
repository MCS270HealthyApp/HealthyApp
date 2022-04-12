package com.healthyorg.android.healthyapp.sleepClasses

import com.healthyorg.android.healthyapp.database.SleepDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.healthyorg.android.healthyapp.R

class SleepActivity : AppCompatActivity() {
    private lateinit var sleepEntryButton: Button
    private lateinit var sleepEditText: EditText

    private val sleepListViewModel: SleepListViewModel by lazy {
        ViewModelProvider(this).get(SleepListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep)

        sleepEntryButton = findViewById(R.id.sleep_enter_button)
        sleepEditText = findViewById(R.id.sleep_edit_text)

        val currentFragment  = supportFragmentManager.findFragmentById(R.id.sleep_fragment_container)

        sleepEntryButton.setOnClickListener{
            sleepListViewModel.addSleep(DailySleepMood(hours = sleepEditText.text.toString().toDouble()))
        }

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