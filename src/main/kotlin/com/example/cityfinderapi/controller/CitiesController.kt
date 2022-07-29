package com.example.cityfinderapi.controller

import com.example.cityfinderapi.service.CityFinderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CitiesController(private val cityFinderService: CityFinderService) {
    @GetMapping("/suggestions")
    fun suggestCities(
        @RequestParam q: String,
        @RequestParam latitude: String? = null,
        @RequestParam longitude: String? = null
    ) = cityFinderService.findCities(q, latitude, longitude)
}