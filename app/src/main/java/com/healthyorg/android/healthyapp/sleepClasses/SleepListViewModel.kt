package com.healthyorg.android.healthyapp.sleepClasses

import androidx.lifecycle.ViewModel

class SleepListViewModel: ViewModel() {
    //Gets the list of sleep objects in our database
    private val sleepRepository = SleepRepository.get()
    val sleepListLiveData = sleepRepository.getAllSleeps()

    //Inserting a new sleep object to the database by wrapping a call to the repository
    fun addSleep(sleep: DailySleepMood){
        sleepRepository.insertSleep(sleep)
    }

    //Deleting a particular sleep object from the database by wrapping a call to the repository
    fun deleteSleep(sleep: DailySleepMood){
        sleepRepository.deleteSleep(sleep)
    }
}