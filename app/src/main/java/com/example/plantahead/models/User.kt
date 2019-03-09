package com.example.plantahead.models

data class User(
    val name: String,
    val listOfPlants: List<Plant>,
    val listOfDevices: List<Device>,
    val password: String,
    val zipCode: Number)