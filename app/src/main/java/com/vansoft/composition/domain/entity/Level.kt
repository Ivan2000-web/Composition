package com.vansoft.composition.domain.entity

import java.io.Serializable

enum class Level: Serializable { //можно и не указывать Serializable, так как это enum-класс
    TEST, EASY, NORMAL, HARD
}
