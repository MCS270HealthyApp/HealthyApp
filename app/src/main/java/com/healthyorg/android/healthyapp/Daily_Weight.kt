package com.healthyorg.android.healthyapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Daily_Weight(
    @ColumnInfo var input_weight: Double,
    @PrimaryKey var date: Date)
