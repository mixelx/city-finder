package com.example.cityfinderapi.component

import com.example.cityfinderapi.dto.RawCity
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import org.springframework.stereotype.Component
import java.io.File

@Component
class CsvCitiesParser {
    fun parseCities(): Set<RawCity> {
        val csvMapper = CsvMapper()
        return csvMapper
            .readerWithTypedSchemaFor(RawCity::class.java)
            .with(csvMapper.buildSchema())
            .readValues<RawCity>(File(CSV_DATASET_FILE_PATH))
            .readAll()
            .toSet()
    }

    private fun CsvMapper.buildSchema() =
        typedSchemaFor(RawCity::class.java)
            .withHeader()
            .withColumnSeparator(SEPARATOR)

    companion object {
        private const val CSV_DATASET_FILE_PATH = "src/main/resources/cities.csv"
        private const val SEPARATOR = ','
    }
}