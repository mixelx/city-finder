package com.example.cityfinderapi.base

import com.example.cityfinderapi.storage.CitiesStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
abstract class BaseIntegrationTest {
    @Autowired
    protected lateinit var storage: CitiesStorage

    @Autowired
    protected lateinit var client: MockMvc
}