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
        val nameScores = calculateWithoutCoordinates(cities, input)
        val distanceMap = cities.associate {
            it.id to calculateDistance(
                requestLatitude = latitude.toDouble(),
                requestLongitude = longitude.toDouble(),
                cityLatitude = it.lat.toDouble(),
                cityLongitude = it.lng.toDouble()
            )
        }
        return cities.associate {
            it.id to calculateCityScoreByDistanceAndName(
                distanceMap[it.id]!!,
                nameScores[it.id]!!
            )
        }
    }

    private fun calculateWithoutCoordinates(
        cities: List<RawCity>,
        input: String,
    ): Map<String, Double> {
        val inputSize = input.length.toDouble()
        return cities.associate { it.id to (inputSize / it.cityAscii.length.toDouble()) }
    }

    private fun calculateCityScoreByDistanceAndName(distance: Double, nameScore: Double): Double {
        val nameScoreValue = nameScore * NAME_MATCHING_PRIORITY_COEFFICIENT
        val distanceRangeScore = DISTANCE_MAP_RANGE_SCORES
            .entries
            .firstOrNull { distance.toInt() in it.key }
            ?.value
            ?: DEFAULT_DISTANCE_SCORE
        val distanceScoreValue = distanceRangeScore * DISTANCE_PRIORITY_COEFFICIENT
        return nameScoreValue + distanceScoreValue
    }

    companion object {
        private val DISTANCE_MAP_RANGE_SCORES = mapOf(
            0..100 to 1.0,
            100..250 to 0.9,
            250..450 to 0.8,
            450..700 to 0.7,
            700..1000 to 0.6,
            1000..1300 to 0.5,
            1300..1700 to 0.4,
            1700..2300 to 0.3,
            2300..3000 to 0.2,
        )
        private const val DISTANCE_PRIORITY_COEFFICIENT = 0.7
        private const val NAME_MATCHING_PRIORITY_COEFFICIENT = 0.3
        private const val DEFAULT_DISTANCE_SCORE = 0.1
    }
}