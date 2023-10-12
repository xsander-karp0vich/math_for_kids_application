package com.example.math_for_kids_application.domain.usecases

import com.example.math_for_kids_application.domain.entities.Question
import com.example.math_for_kids_application.domain.repository.GameRepository
import kotlin.math.max

class GenerateQuestionUseCase (
    private val repository: GameRepository
        ) {

    operator fun invoke(maxSum:Int): Question {
       return repository.generateQuestion(maxSum, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}