package com.healthyorg.android.healthyapp.weightClasses

import androidx.lifecycle.ViewModel

class WeightListViewModel: ViewModel() {
    private val weightRepository = WeightRepository.get()
    val weightListLiveData = weightRepository.getAllWeights()

    fun addWeight(weight: Daily_Weight){
        weightRepository.insertWeight(weight)
    }

    fun deleteWeight(weight: Daily_Weight){
        weightRepository.deleteWeight(weight)
    }
}