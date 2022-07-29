package com.example.cityfinderapi.storage

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertTrue

@SpringBootTest
class StorageTest {
    @Autowired
    private lateinit var storage: CitiesStorage

    @Test
    fun `should populate cities field after application start up`() {
        assertTrue(storage.findByStartsWith("A").isNotEmpty())
    }

    @ParameterizedTest
    @ValueSource(strings = ["min", "lOn", "Ber", "Q", "Ro"])
    fun `should return city by starting characters (case insensitive)`(input: String) {
        val result = storage.findByStartsWith(input)
        assertTrue(result.all { it.cityAscii.lowercase().startsWith(input.lowercase()) })
        assertTrue(result.isNotEmpty())
    }
}