package com.example.cityfinderapi.exception

class LongitudeLatitudeNullabilityException(private val msg: String = MESSAGE) : RuntimeException(msg) {
    companion object {
        private const val MESSAGE = "Either both latitude and longitude must present or both must be null"
    }
}