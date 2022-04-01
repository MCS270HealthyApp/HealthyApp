package com.healthyorg.android.healthyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class WeightActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)

        val currentFragment  = supportFragmentManager.findFragmentById(R.id.weight_fragment_container)

        if(currentFragment == null){
            val fragment = WeightListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.weight_fragment_container, fragment).commit()
        }
    }
}