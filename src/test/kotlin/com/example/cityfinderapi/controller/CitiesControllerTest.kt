package com.example.cityfinderapi.controller

import com.example.cityfinderapi.base.BaseIntegrationTest
import com.example.cityfinderapi.dto.CityResponseDto
import com.example.cityfinderapi.dto.ErrorDto
import com.example.cityfinderapi.utils.COORDINATES_INPUT_EXCEPTION
import com.example.cityfinderapi.utils.NULLABILITY_EXCEPTION_MESSAGE
import com.example.cityfinderapi.utils.fromJson
import com.example.cityfinderapi.utils.listFromJson
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.get
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CitiesControllerTest : BaseIntegrationTest() {
    @Test
    fun `should return all cities starting from provided input`() {
        val input = "mos"
        val response = findCities(input)

        assertTrue(response.isNotEmpty())
        assertTrue(response.all { it.name.lowercase().startsWith(input) })
    }

    @Test
    fun `should return an empty response if nothing was found`() {
        val input = "1234567890qwerty"
        val response = findCities(input)

        assertTrue(response.isEmpty())
    }

    @Test
    fun `should place Minsk at first place in response if passed coordinates is near to Minsk`() {
        val userLatitude = "53.956400031699765"
        val userLongitude = "27.664240773151477"
        val input = "min"
        val response = findCities(input, userLatitude, userLongitude)

        assertTrue(response.isNotEmpty())
        assertTrue(response.all { it.name.lowercase().startsWith(input) })
        assertTrue(response.first().name == "Minsk, BLR")
    }

    @Test
    fun `should return an error when pass latitude and do not pass longitude`() {
        val latitude = "27.664240773151477"
        val input = "London"
        val response = getSearchResponse(input, latitude)

        assertEquals(400, response.status)
        assertEquals(NULLABILITY_EXCEPTION_MESSAGE, response.parseErrorMessage())
    }

    @Test
    fun `should return an error when pass longitude and do not pass latitude`() {
        val longitude = "27.664240773151477"
        val input = "London"
        val response = getSearchResponse(input, longitude = longitude)

        assertEquals(400, response.status)
        assertEquals(NULLABILITY_EXCEPTION_MESSAGE, response.parseErrorMessage())
    }

    @ParameterizedTest
    @ValueSource(strings = ["-90.01", "-150.123", "90.01", "179.23"])
    fun `should return an error when pass incorrect latitude value`(latitude: String) {
        val longitude = "27.664240773151477"
        val input = "London"
        val response = getSearchResponse(input, latitude, longitude)

        assertEquals(400, response.status)
        assertEquals(COORDINATES_INPUT_EXCEPTION, response.parseErrorMessage())
    }

    @ParameterizedTest
    @ValueSource(strings = ["-180.01", "-200.9", "180.01", "222.22"])
    fun `should return an error when pass incorrect longitude value`(longitude: String) {
        val latitude = "27.664240773151477"
        val input = "London"
        val response = getSearchResponse(input, latitude, longitude)

        assertEquals(400, response.status)
        assertEquals(COORDINATES_INPUT_EXCEPTION, response.parseErrorMessage())
    }

    @ParameterizedTest
    @ValueSource(strings = ["-90.00", "-50.11", "50.11", "90.00"])
    fun `shouldn't fail when pass correct latitude`(latitude: String) {
        val longitude = "27.664240773151477"
        val input = "London"
        val response = findCities(input, latitude, longitude)

        assertTrue(response.isNotEmpty())
    }

    @ParameterizedTest
    @ValueSource(strings = ["-180.00", "-50.11", "50.11", "180.00"])
    fun `shouldn't fail when pass correct longitude`(longitude: String) {
        val latitude = "27.664240773151477"
        val input = "London"
        val response = findCities(input, latitude, longitude)

        assertTrue(response.isNotEmpty())
    }

    private fun findCities(query: String, latitude: String? = null, longitude: String? = null) =
        getSearchResponse(query, latitude, longitude)
            .contentAsString
            .listFromJson<CityResponseDto>()

    private fun MockHttpServletResponse.parseErrorMessage() = contentAsString.fromJson<ErrorDto>().message

    private fun getSearchResponse(query: String, latitude: String? = null, longitude: String? = null) =
        client.get(API_PATH) {
            param("q", query)
            if (latitude != null) param("latitude", latitude)
            if (longitude != null) param("longitude", longitude)
        }.andReturn()
            .response

    companion object {
        private const val API_PATH = "/suggestions"
    }
}