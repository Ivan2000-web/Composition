package com.vansoft.composition.domain.usecases

import com.vansoft.composition.domain.entity.GameSettings
import com.vansoft.composition.domain.entity.Question
import com.vansoft.composition.domain.repository.GameRepository

class GenerateQuestionUseCase (private val repository: GameRepository) {
        operator fun invoke(maxSumValue: Int): Question{ //invoke позволяет работать с use case как с методом
            return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
        }

        private companion object {
            private const val COUNT_OF_OPTIONS = 6 //Кол-во вариантов ответа в игре
        }
    }
