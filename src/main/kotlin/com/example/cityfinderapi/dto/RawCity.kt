package com.example.cityfinderapi.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("city", "city_ascii", "lat", "lng", "country", "iso2", "iso3", "admin_name", "capital", "population", "id")
data class RawCity(
    val city: String = EMPTY,
    @set:JsonProperty("city_ascii")
    var cityAscii: String = EMPTY,
    val lat: String = EMPTY,
    val lng: String = EMPTY,
    val country: String = EMPTY,
    val iso2: String = EMPTY,
    val iso3: String = EMPTY,
    @set:JsonProperty("admin_name")
    var adminName: String = EMPTY,
    val capital: String = EMPTY,
    val population: String = EMPTY,
    val id: String = EMPTY,
) {
    fun toResponse(score: Double) =
        CityResponseDto(
            name = cityAscii,
            latitude = lat,
            longitude = lng,
            score = score
        )

    companion object {
        private const val EMPTY = ""
    }
}