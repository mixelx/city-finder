package com.example.cityfinderapi.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL

@JsonInclude(NON_NULL)
data class CityResponseDto(
    val name: String,
    val latitude: String,
    val longitude: String,
    val distance: String? = null,
    val score: Double,
)