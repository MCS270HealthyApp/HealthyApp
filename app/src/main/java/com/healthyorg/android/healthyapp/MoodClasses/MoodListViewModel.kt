package com.healthyorg.android.healthyapp.MoodClasses

import androidx.lifecycle.ViewModel
import com.healthyorg.android.healthyapp.MoodClasses.MoodRepository

class MoodListViewModel: ViewModel() {
    private val moodRepository = MoodRepository.get()
    val moodListLiveData = moodRepository.getAllMoods()

    fun addMood(mood: Daily_Mood){
        moodRepository.insertMood(mood)
    }
}