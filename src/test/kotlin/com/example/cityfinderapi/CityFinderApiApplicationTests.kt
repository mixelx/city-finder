package com.example.cityfinderapi

import com.example.cityfinderapi.utils.calculateDistance
import org.junit.jupiter.api.Test

//@SpringBootTest
class CityFinderApiApplicationTests {

    @Test
    fun contextLoads() {
        val result = calculateDistance(53.58920499357366, 28.398813337753456, 53.504810199254266, 26.89526959474775)
        println(result)
    }


}
