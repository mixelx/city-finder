package com.example.cityfinderapi.controller

import com.example.cityfinderapi.dto.ErrorDto
import com.example.cityfinderapi.exception.LongitudeLatitudeInputException
import com.example.cityfinderapi.exception.LongitudeLatitudeNullabilityException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class AdviceController {
    @ExceptionHandler
    fun LongitudeLatitudeInputException.handleException(httpRequest: HttpServletRequest): ResponseEntity<ErrorDto> {
        log()
        return ResponseEntity.badRequest().body(buildErrorDto(BAD_REQUEST, httpRequest, message))
    }

    @ExceptionHandler
    fun LongitudeLatitudeNullabilityException.handleException(httpRequest: HttpServletRequest): ResponseEntity<ErrorDto> {
        log()
        return ResponseEntity.badRequest().body(buildErrorDto(BAD_REQUEST, httpRequest, message))
    }

    @ExceptionHandler
    fun Exception.handleException(httpRequest: HttpServletRequest): ResponseEntity<ErrorDto> {
        log()
        return ResponseEntity.badRequest().body(buildErrorDto(INTERNAL_SERVER_ERROR, httpRequest, message))
    }

    private fun buildErrorDto(status: HttpStatus, httpRequest: HttpServletRequest, message: String?) =
        ErrorDto(
            status = status,
            statusCode = status.value(),
            path = httpRequest.requestURI,
            message = message ?: DEFAULT_ERROR_MESSAGE
        )

    private fun Exception.log() = log.error { "${this::class.simpleName} : ${this.message}" }

    companion object {
        private val log = KotlinLogging.logger {}
        private const val DEFAULT_ERROR_MESSAGE = "Something went wrong. Please try again later."
    }
}