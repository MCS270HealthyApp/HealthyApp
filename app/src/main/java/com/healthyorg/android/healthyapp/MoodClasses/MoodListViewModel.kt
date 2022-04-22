package com.healthyorg.android.healthyapp.MoodClasses

import androidx.lifecycle.ViewModel
import com.healthyorg.android.healthyapp.MoodClasses.MoodRepository
import com.healthyorg.android.healthyapp.sleepClasses.DailySleepMood

class MoodListViewModel: ViewModel() {
    private val moodRepository = MoodRepository.get()
    val moodListLiveData = moodRepository.getAllMoods()

    fun addMood(mood: Daily_Mood){
        moodRepository.insertMood(mood)
    }

    fun deleteMood(mood: Daily_Mood){
        moodRepository.deleteMood(mood)
    }
}