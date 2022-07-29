package com.example.cityfinderapi.service

import com.example.cityfinderapi.dto.RawCity
import com.example.cityfinderapi.utils.calculateDistance
import org.springframework.stereotype.Service

@Service
class ScoreCalculationService {
    /**
     * Calculates each provided city score and returns map [CITY_UNIQUE_ID => CITY_SCORE]
     */
    fun calculateScores(
        cities: List<RawCity>,
        input: String,
        longitude: String? = null,
        latitude: String? = null
    ): Map<String, Double> {
        return longitude?.let {
            calculateWithCoordinates(it, latitude!!, cities, input)
        } ?: calculateWithoutCoordinates(cities, input)
    }

    private fun calculateWithCoordinates(
        longitude: String,
        latitude: String,
        cities: List<RawCity>,
        input: String
    ): Map<String, Double> {
        val distanceMap = cities.associateBy {
            calculateDistance(
                requestLatitude = latitude.toDouble(),
                requestLongitude = longitude.toDouble(),
                cityLatitude = it.lat.toDouble(),
                cityLongitude = it.lng.toDouble()
            )
        }
        return cities.associate { it.id to 1.0 }
    }

    private fun calculateWithoutCoordinates(
        cities: List<RawCity>,
        input: String,
    ): Map<String, Double> {
        TODO()
    }
}