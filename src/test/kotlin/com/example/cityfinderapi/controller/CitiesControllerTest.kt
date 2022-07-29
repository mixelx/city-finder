package com.example.cityfinderapi.controller

import com.example.cityfinderapi.base.BaseIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.get

class CitiesControllerTest : BaseIntegrationTest() {
    @Test
    fun `should return all cities starting from provided input`() {
        val input = "mos"
        val response = client.get(API_PATH) {
            params?.add("q", input)
        }
    }

    companion object {
        private const val API_PATH = "/suggestions"
    }
}