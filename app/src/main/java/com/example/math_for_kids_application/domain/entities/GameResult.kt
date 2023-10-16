package com.example.math_for_kids_application.domain.entities

import java.io.Serializable
 data class GameResult (
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfAnswers: Int,
    val gameSettings: GameSettings
        ) : Serializable