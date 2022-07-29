package com.example.cityfinderapi.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.text.DecimalFormat

@JsonPropertyOrder(
    "city",
    "city_ascii",
    "lat",
    "lng",
    "country",
    "iso2",
    "iso3",
    "admin_name",
    "capital",
    "population",
    "id"
)
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
    fun toResponse(score: Double, distance: Double? = null) =
        CityResponseDto(
            name = "$cityAscii, $iso3",
            latitude = lat,
            longitude = lng,
            score = DECIMAL_FORMAT.format(score).replace(',', '.').toDouble(),
            distance = distance?.let { "${it.toInt()} km" }
        )

    companion object {
        private const val EMPTY = ""
        private val DECIMAL_FORMAT = DecimalFormat("#.##")
    }
}