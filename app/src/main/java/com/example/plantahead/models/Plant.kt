package com.example.plantahead.models

data class Plant(
    val name: String,
    val photoUrl: String,
    val description: String,
    val brightnessLevel: Number,
    val humidityLevel: Number,
    val timestamp: Number)