package com.healthyorg.android.healthyapp

import android.app.Application

class HealthyAppApplication: Application() {
    override fun onCreate(){
        super.onCreate()
        WeightRepository.initialize(this)
        SleepRepository.initialize(this)
    }
}