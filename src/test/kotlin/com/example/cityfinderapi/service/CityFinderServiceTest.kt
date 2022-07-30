package com.example.cityfinderapi.service

import com.example.cityfinderapi.base.BaseUnitInstanced
import com.example.cityfinderapi.storage.CitiesStorage
import com.example.cityfinderapi.utils.createRawCity
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CityFinderServiceTest : BaseUnitInstanced<CityFinderService>() {
    @RelaxedMockK
    private lateinit var storage: CitiesStorage

    @RelaxedMockK
    private lateinit var scoreCalculationService: ScoreCalculationService

    @Test
    fun `should return list of cities with all required fields`() {
        val searchInput = "mosc"
        val cities = createListOfCities()
        every { storage.findByStartsWith(searchInput) } returns cities
        every { scoreCalculationService.calculateScores(cities, searchInput) } returns mapOf(
            "1" to 1.0,
            "2" to 0.5,
            "3" to 0.3
        )
        val result = testedInstance.findCities(searchInput)

        verify { storage.findByStartsWith(searchInput) }
        verify { scoreCalculationService.calculateScores(cities, searchInput) }
        assertEquals(3, result.size)
        assertEquals(1.0, result.first().score)
    }

    private fun createListOfCities() =
        listOf(
            createRawCity(cityAscii = "Moscow1", iso3 = "RUS", lat = "1.0", lng = "1.0", id = "1"),
            createRawCity(cityAscii = "Moscow2", iso3 = "RUS", lat = "1.0", lng = "1.0", id = "2"),
            createRawCity(cityAscii = "Moscow3", iso3 = "RUS", lat = "1.0", lng = "1.0", id = "3"),
        )
}