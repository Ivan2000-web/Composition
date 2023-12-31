package com.vansoft.composition.domain.repository

import com.vansoft.composition.domain.entity.GameSettings
import com.vansoft.composition.domain.entity.Level
import com.vansoft.composition.domain.entity.Question

interface GameRepository {
    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ) : Question
    fun getGameSettings(level: Level): GameSettings
}