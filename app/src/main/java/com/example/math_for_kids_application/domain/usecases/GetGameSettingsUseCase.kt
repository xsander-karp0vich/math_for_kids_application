package com.example.math_for_kids_application.domain.usecases

import com.example.math_for_kids_application.domain.entities.GameSettings
import com.example.math_for_kids_application.domain.entities.Level
import com.example.math_for_kids_application.domain.repository.GameRepository

class GetGameSettingsUseCase (
    private val repository: GameRepository
        ) {

    operator fun invoke (level:Level): GameSettings {
        return repository.getGameSettings(level)
    }
}