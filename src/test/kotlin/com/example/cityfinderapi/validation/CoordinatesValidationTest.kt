package com.example.cityfinderapi.validation

import com.example.cityfinderapi.exception.LongitudeLatitudeInputException
import com.example.cityfinderapi.exception.LongitudeLatitudeNullabilityException
import com.example.cityfinderapi.utils.COORDINATES_INPUT_EXCEPTION
import com.example.cityfinderapi.utils.NULLABILITY_EXCEPTION_MESSAGE
import com.example.cityfinderapi.validator.validateCoordinates
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

@TestInstance(PER_CLASS)
class CoordinatesValidationTest {
    @Test
    fun `should throw exception when longitude is null and latitude not`() {
        val exception = assertThrows<LongitudeLatitudeNullabilityException> {
            validateCoordinates("20.00", null)
        }
        assertEquals(exception.message, NULLABILITY_EXCEPTION_MESSAGE)
    }

    @Test
    fun `should throw exception when latitude is null and longitude not`() {
        val exception = assertThrows<LongitudeLatitudeNullabilityException> {
            validateCoordinates(null, "20.00")
        }
        assertEquals(exception.message, NULLABILITY_EXCEPTION_MESSAGE)
    }

    @ParameterizedTest
    @ValueSource(strings = ["-90.01", "-150.123", "90.01", "179.23"])
    fun `should throw exception when pass incorrect latitude value`(latitude: String) {
        val exception = assertThrows<LongitudeLatitudeInputException> {
            validateCoordinates(latitude, "20.00")
        }
        assertEquals(exception.message, COORDINATES_INPUT_EXCEPTION)
    }

    @ParameterizedTest
    @ValueSource(strings = ["-180.01", "-200.9", "180.01", "222.22"])
    fun `should throw exception when pass incorrect longitude value`(longitude: String) {
        val exception = assertThrows<LongitudeLatitudeInputException> {
            validateCoordinates("20.00", longitude)
        }
        assertEquals(exception.message, COORDINATES_INPUT_EXCEPTION)
    }

    @ParameterizedTest
    @ValueSource(strings = ["-90.00", "-50.11", "50.11", "90.00"])
    fun `shouldn't fail when pass correct latitude`(latitude: String) {
        assertDoesNotThrow { validateCoordinates(latitude, "20.00") }
    }

    @ParameterizedTest
    @ValueSource(strings = ["-180.00", "-50.11", "50.11", "180.00"])
    fun `shouldn't fail when pass correct longitude`(longitude: String) {
        assertDoesNotThrow { validateCoordinates("20.00", longitude) }
    }
}