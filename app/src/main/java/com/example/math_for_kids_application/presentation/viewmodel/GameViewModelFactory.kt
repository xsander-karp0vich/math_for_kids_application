package com.example.math_for_kids_application.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.math_for_kids_application.domain.entities.Level

class GameViewModelFactory (
    private val level:Level,
    private val application: Application
        ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameFragmentViewModel::class.java)){
            return GameFragmentViewModel(application,level) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}