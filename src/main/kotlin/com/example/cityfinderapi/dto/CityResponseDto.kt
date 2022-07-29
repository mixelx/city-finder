package com.example.cityfinderapi.dto

data class CityResponseDto(
    val name: String,
    val latitude: String,
    val longitude: String,
    val score: Double,
)