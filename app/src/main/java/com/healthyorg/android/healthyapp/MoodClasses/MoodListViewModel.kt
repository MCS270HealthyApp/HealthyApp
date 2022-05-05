package com.healthyorg.android.healthyapp.MoodClasses

import androidx.lifecycle.ViewModel
import com.healthyorg.android.healthyapp.MoodClasses.MoodRepository
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood

class MoodListViewModel: ViewModel() {
    //Gets the list of moods in our database
    private val moodRepository = MoodRepository.get()
    val moodListLiveData = moodRepository.getAllMoods()

    //Inserting a new mood to the database by wrapping a call to the repository
    fun addMood(mood: Daily_Mood){
        moodRepository.insertMood(mood)
    }

    //Deleting a particular mood from the database by wrapping a call to the repository
    fun deleteMood(mood: Daily_Mood){
        moodRepository.deleteMood(mood)
    }
}