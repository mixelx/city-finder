package com.example.cityfinderapi.validator

import com.example.cityfinderapi.exception.LongitudeLatitudeInputException
import com.example.cityfinderapi.exception.LongitudeLatitudeNullabilityException

private const val LATITUDE_MIN_VALUE = -90.0
private const val LATITUDE_MAX_VALUE = 90.0
private const val LONGITUDE_MIN_VALUE = -180.0
private const val LONGITUDE_MAX_VALUE = 180.0

/**
 * Requires coordinates to be in their possible values range.
 */
fun validateCoordinates(latitude: String? = null, longitude: String? = null) {
    validateCoordinatesOnNullability(latitude, longitude)
    if (latitude == null && longitude == null) return;
    if (latitude!!.toDouble().isValidLatitude() && longitude!!.toDouble().isValidLongitude()) {
        return
    } else {
        throw LongitudeLatitudeInputException();
    }
}

/**
 * Checks if either both are null or both are not null.
 * In case if one is null and second not - throws an exception.
 */
private fun validateCoordinatesOnNullability(latitude: String? = null, longitude: String? = null) {
    if ((latitude == null).xor(longitude == null)) {
        throw LongitudeLatitudeNullabilityException()
    }
}

private fun Double.isValidLatitude() =
    this in LATITUDE_MIN_VALUE..LATITUDE_MAX_VALUE

private fun Double.isValidLongitude() =
    this in LONGITUDE_MIN_VALUE..LONGITUDE_MAX_VALUE
