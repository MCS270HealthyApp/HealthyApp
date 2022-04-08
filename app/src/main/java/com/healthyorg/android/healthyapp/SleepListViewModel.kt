package com.healthyorg.android.healthyapp

import androidx.lifecycle.ViewModel

class SleepListViewModel: ViewModel() {
    private val sleepRepository = SleepRepository.get()
    val sleepListLiveData = sleepRepository.getAllSleeps()

    fun addSleep(sleep: DailySleepMood){
        sleepRepository.insertSleep(sleep)
    }
}