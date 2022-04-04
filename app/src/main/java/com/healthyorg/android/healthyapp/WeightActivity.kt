package com.healthyorg.android.healthyapp

import com.healthyorg.android.healthyapp.database.WeightDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.healthyorg.android.healthyapp.WeightListFragment

class WeightActivity : AppCompatActivity() {
    private lateinit var weightEntryButton: Button
    private lateinit var weightEditText: EditText

    private val weightListViewModel: WeightListViewModel by lazy {
        ViewModelProviders.of(this).get(WeightListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)

        weightEntryButton = findViewById(R.id.weight_submit_button)
        weightEditText = findViewById(R.id.weight_entry)

        val currentFragment  = supportFragmentManager.findFragmentById(R.id.weight_fragment_container)

        weightEntryButton.setOnClickListener{
            weightListViewModel.addWeight(Daily_Weight(input_weight = weightEditText.text.toString().toDouble()))
        }

        if(currentFragment == null){
            val fragment = WeightListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.weight_fragment_container, fragment).commit()
        }
        val db = Room.databaseBuilder(
            applicationContext,
            WeightDatabase::class.java, "database-name"
        ).build()
    }
}