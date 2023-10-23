package com.example.math_for_kids_application.presentation.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.math_for_kids_application.R
import com.example.math_for_kids_application.data.repositoryImpl.GameRepositoryImpl
import com.example.math_for_kids_application.domain.entities.GameResult
import com.example.math_for_kids_application.domain.entities.GameSettings
import com.example.math_for_kids_application.domain.entities.Level
import com.example.math_for_kids_application.domain.entities.Question
import com.example.math_for_kids_application.domain.repository.GameRepository
import com.example.math_for_kids_application.domain.usecases.GenerateQuestionUseCase
import com.example.math_for_kids_application.domain.usecases.GetGameSettingsUseCase

class GameFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GameRepositoryImpl

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val context = application

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var preGameTimer: CountDownTimer? = null
    private var gameTimer: CountDownTimer? = null

    private val _formattedPreGameTime = MutableLiveData<String>()
    val formattedPreGameTime: LiveData<String>
        get() = _formattedPreGameTime

    private val _formattedGameTime = MutableLiveData<String>()
    val formattedGameTime: LiveData<String>
        get() = _formattedGameTime

    private var _isGameStarted = MutableLiveData<Boolean>()
    val isGameStarted: LiveData<Boolean>
        get() = _isGameStarted

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var countOfRightAnswers: Int = 0
    private var countOfQuestions: Int = 0

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult


    fun startGame(level: Level) {
        getGameSettings(level)
        startPreGameTimer()
        generateQuestion()
        updateProgress()
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    fun chooseAnswers(number: Int) {
        checkAnswers(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswers(number: Int) {
        val rightAnswer= question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.correct_answers_s_minimum_s),
            countOfRightAnswers,
            gameSettings.minimumNumsOfPointsToWin
        )
        _enoughCountOfRightAnswers.value = countOfRightAnswers >= gameSettings.minimumNumsOfPointsToWin
        _enoughPercentOfRightAnswers.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int{
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startPreGameTimer() {

        preGameTimer = object : CountDownTimer(
            PRE_GAME_TIME, //3000 mils
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedPreGameTime.value = formatPreGameTime(millisUntilFinished)
            }
            override fun onFinish() {
                _isGameStarted.value = true
                startGameTimer()
            }
        }
        preGameTimer?.start()

    }

    private fun startGameTimer() {
        gameTimer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedGameTime.value = formatGameTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        gameTimer?.start()
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCountOfRightAnswers.value == true && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings

        )
    }


    private fun formatGameTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun formatPreGameTime(millisUntilFinished: Long): String{
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        return seconds.toString()
    }

    override fun onCleared() {
        super.onCleared()
        gameTimer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val PRE_GAME_TIME = 4000L

        private const val SECONDS_IN_MINUTES = 60
    }
}