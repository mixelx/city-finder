package com.example.cityfinderapi.dto

import org.springframework.http.HttpStatus

data class ErrorDto(
    val statusCode: Int,
    val status: HttpStatus,
    val path: String,
    val message: String
)