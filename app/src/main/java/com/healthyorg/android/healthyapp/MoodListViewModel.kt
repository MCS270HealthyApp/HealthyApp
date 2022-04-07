package com.healthyorg.android.healthyapp

import androidx.lifecycle.ViewModel

class MoodListViewModel: ViewModel() {
    private val moodRepository = MoodRepository.get()
    val moodListLiveData = moodRepository.getAllMoods()

    fun addMood(mood: Daily_Mood){
        moodRepository.insertMood(mood)
    }
}