package com.healthyorg.android.healthyapp

import androidx.lifecycle.ViewModel

class WeightListViewModel: ViewModel() {
    val weights = mutableListOf<Daily_Weight>()

    init{
        TODO("implement initialization of list using stored weight values")
    }
}