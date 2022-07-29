package com.example.cityfinderapi.exception

class LongitudeLatitudeInputException(private val msg: String = MESSAGE) : RuntimeException(msg) {
    companion object {
        private const val MESSAGE = "Latitude value must be between -90 and 90. Longitude value must be between -180 and 180."
    }
}