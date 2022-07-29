package com.example.cityfinderapi.storage

import com.example.cityfinderapi.component.CsvCitiesParser
import com.example.cityfinderapi.dto.RawCity
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CitiesStorage(private val cityParser: CsvCitiesParser) {
    private val cities = mutableSetOf<RawCity>()

    @EventListener(ApplicationReadyEvent::class)
    fun initStorage() {
        if (cities.isEmpty()) {
            try{
                log.info { "Starting parsing cities file..." }
                cities.addAll(cityParser.parseCities())
                log.info { "${cities.size} cities has been parsed successfully!" }
            } catch (ex: Exception) {
                log.error { "Caught exception during parsing cities file: $ex" }
            }
        }
    }

    fun findByStartsWith(input: String) =
        cities.filter { it.cityAscii.startsWith(input) }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}