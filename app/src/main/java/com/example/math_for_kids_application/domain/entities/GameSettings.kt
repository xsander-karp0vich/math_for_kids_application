package com.example.math_for_kids_application.domain.entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GameSettings (
    val maxSumValue: Int,
    val minimumNumsOfPointsToWin: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
        ) : Parcelable
