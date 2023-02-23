package com.example.bindimaps.model

data class MainUserSessionsItem(
    val endTimeUtc: String,
    val path: List<Path>,
    val sessionId: String,
    val startTimeLocal: String,
    val startTimeUtc: String,
    val userId: String
)