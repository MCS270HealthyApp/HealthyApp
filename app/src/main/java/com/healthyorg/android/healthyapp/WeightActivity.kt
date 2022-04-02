package com.healthyorg.android.healthyapp

import com.healthyorg.android.healthyapp.database.WeightDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.healthyorg.android.healthyapp.WeightListFragment

class WeightActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)

        val currentFragment  = supportFragmentManager.findFragmentById(R.id.weight_fragment_container)

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