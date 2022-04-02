package com.healthyorg.android.healthyapp

import androidx.lifecycle.ViewModel

class WeightListViewModel: ViewModel() {
    private val weightRepository = WeightRepository.get()
    val weights = weightRepository.getAllWeights().toList()
}