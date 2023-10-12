package com.example.math_for_kids_application.domain.repository

import com.example.math_for_kids_application.domain.entities.GameSettings
import com.example.math_for_kids_application.domain.entities.Level
import com.example.math_for_kids_application.domain.entities.Question

interface GameRepository {

    fun generateQuestion (
        maxSumValue: Int, countOfOptions: Int
    ): Question

    fun getGameSettings (level: Level): GameSettings
}