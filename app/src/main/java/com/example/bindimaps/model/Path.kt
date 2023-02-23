package com.example.bindimaps.model

import java.io.Serializable

data class Path(
    val position: Position,
    val userTimeUtc: String
) : Serializable