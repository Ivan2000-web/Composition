package com.vansoft.composition.domain.entity

data class GameResult (
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val gameStings: GameSettings
)