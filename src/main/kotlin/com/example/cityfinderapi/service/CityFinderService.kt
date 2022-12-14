package com.example.cityfinderapi.service

import com.example.cityfinderapi.dto.CityResponseDto
import com.example.cityfinderapi.dto.RawCity
import com.example.cityfinderapi.storage.CitiesStorage
import com.example.cityfinderapi.utils.calculateDistance
import com.example.cityfinderapi.validator.validateCoordinates
import org.springframework.stereotype.Service

@Service
class CityFinderService(
    private val storage: CitiesStorage,
    private val scoreCalculationService: ScoreCalculationService
) {
    fun findCities(input: String, latitude: String? = null, longitude: String? = null): Set<CityResponseDto> {
        validateCoordinates(latitude, longitude)
        val cities = storage.findByStartsWith(input)
        val scoresMap = scoreCalculationService.calculateScores(cities, input, longitude, latitude)
        return cities
            .map {
                it.toResponse(
                    score = scoresMap[it.id]!!,
                    distance = it.calculateDistance(latitude, longitude)
                )
            }
            .sortedByDescending { it.score }
            .toSet()
    }

    private fun RawCity.calculateDistance(latitude: String? = null, longitude: String? = null) =
        latitude?.let {
            calculateDistance(
                requestLatitude = it.toDouble(),
                requestLongitude = longitude!!.toDouble(),
                cityLatitude = lat.toDouble(),
                cityLongitude = lng.toDouble()
            )
        }
}