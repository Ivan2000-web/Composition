package com.vansoft.composition.domain.usecases

import com.vansoft.composition.domain.entity.GameSettings
import com.vansoft.composition.domain.entity.Level
import com.vansoft.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}