package com.example.math_for_kids_application.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level :Parcelable {
    TEST, EASY, NORMAL, HARD
}