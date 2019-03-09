package com.example.plantahead.models

data class Plant(
    val name: String,
    val photoUrl: String,
    val description: String,
    val brightnessLevel: String,
    val humidityLevel: String,
    val temperatureLevel: String,
    val timestamp: String) {
    constructor() : this("", "", "", "", "", "", "")
}