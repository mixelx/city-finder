package com.example.cityfinderapi.storage

import com.example.cityfinderapi.component.CsvCitiesParser
import com.example.cityfinderapi.dto.RawCity
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CitiesStorage(private val cityParser: CsvCitiesParser) {
    private val cities = mutableSetOf<RawCity>()

    @EventListener(ApplicationReadyEvent::class)
    fun initStorage() {
        if (cities.isEmpty()) {
            cities.addAll(cityParser.parseCities())
        }
    }

    fun findByStartsWith(input: String) =
        cities.filter { it.cityAscii.startsWith(input) }
}