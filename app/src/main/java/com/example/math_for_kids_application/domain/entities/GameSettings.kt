package com.example.math_for_kids_application.domain.entities

import java.io.Serializable

data class GameSettings (
    val maxSumValue: Int,
    val minimumNumsOfPointsToWin: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
        ) : Serializable
