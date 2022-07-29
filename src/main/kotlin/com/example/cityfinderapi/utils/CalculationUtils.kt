package com.example.cityfinderapi.utils

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private const val EARTH_RADIUS = 6371

/**
 * Calculates distance between city and point provided in request.
 * Returns distance in km.
 */
fun calculateDistance(
    requestLatitude: Double,
    requestLongitude: Double,
    cityLatitude: Double,
    cityLongitude: Double
): Double {
    val requestLatitudeRadians = Math.toRadians(requestLatitude)
    val requestLongitudeRadians = Math.toRadians(requestLongitude)
    val cityLatitudeRadians = Math.toRadians(cityLatitude)
    val cityLongitudeRadians = Math.toRadians(cityLongitude)

    val longitudeDistance = cityLongitudeRadians - requestLongitudeRadians
    val latitudeDistance = cityLatitudeRadians - requestLatitudeRadians

    val halfLatitudeSinusMultiply = sin(latitudeDistance / 2) * sin(latitudeDistance / 2)
    val halfLongitudeSinusMultiply = sin(longitudeDistance / 2) * sin(longitudeDistance / 2)
    val requestAndCityLatitudeCosMultiply = cos(requestLatitudeRadians) * cos(cityLatitudeRadians)
    // sorry for these not self-descriptive variables -- i'm not good in trigonometry
    // and got no idea what them are supposed to mean.
    val a = halfLatitudeSinusMultiply + (requestAndCityLatitudeCosMultiply * halfLongitudeSinusMultiply)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return EARTH_RADIUS * c;
}