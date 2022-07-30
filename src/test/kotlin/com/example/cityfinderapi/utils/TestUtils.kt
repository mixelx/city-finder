package com.example.cityfinderapi.utils

import com.example.cityfinderapi.dto.CityResponseDto
import com.example.cityfinderapi.dto.RawCity
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

const val NULLABILITY_EXCEPTION_MESSAGE =
    "Either both latitude and longitude must present or both must be null"
const val COORDINATES_INPUT_EXCEPTION =
    "Latitude value must be between -90 and 90. Longitude value must be between -180 and 180."

const val EMPTY = ""

val objectMapper = ObjectMapper().registerKotlinModule()

inline fun <reified T> String.fromJson() = objectMapper.readValue(this, T::class.java)

inline fun <reified T> String.listFromJson() = objectMapper.readValue(this, Array<CityResponseDto>::class.java)

fun createRawCity(
    city: String = EMPTY,
    cityAscii: String = EMPTY,
    lat: String = EMPTY,
    lng: String = EMPTY,
    country: String = EMPTY,
    iso2: String = EMPTY,
    iso3: String = EMPTY,
    adminName: String = EMPTY,
    capital: String = EMPTY,
    population: String = EMPTY,
    id: String = EMPTY,
) = RawCity(
    city = city,
    cityAscii = cityAscii,
    lat = lat,
    lng = lng,
    country = country,
    iso2 = iso2,
    iso3 = iso3,
    adminName = adminName,
    capital = capital,
    population = population,
    id = id
)