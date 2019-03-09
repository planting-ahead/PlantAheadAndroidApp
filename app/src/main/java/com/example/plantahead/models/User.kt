package com.example.plantahead.models

data class User(
    val id: Number,
    val name: String,
    val listOfPlants: List<Plant>,
    val listOfDevices: List<Device>,
    val zipCode: Number)