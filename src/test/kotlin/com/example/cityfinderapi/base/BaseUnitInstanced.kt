package com.example.cityfinderapi.base

import io.mockk.MockKAnnotations.init
import io.mockk.clearAllMocks
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
open class BaseUnitInstanced<T : Any> {
    @InjectMockKs
    protected lateinit var testedInstance: T

    @BeforeAll
    fun setUp() = init(this, relaxUnitFun = true)

    @AfterEach
    fun clearMocks() = clearAllMocks()
}