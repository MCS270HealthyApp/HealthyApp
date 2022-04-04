package com.healthyorg.android.healthyapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class WorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, WorkoutActivity::class.java)
        }
    }
}