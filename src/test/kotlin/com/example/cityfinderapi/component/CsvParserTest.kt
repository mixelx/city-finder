package com.example.cityfinderapi.component

import com.example.cityfinderapi.base.BaseUnitInstanced
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class CsvParserTest : BaseUnitInstanced<CsvCitiesParser>() {
    @Test
    fun `should parse cities successfully`() {
        assertTrue(testedInstance.parseCities().isNotEmpty())
    }
}