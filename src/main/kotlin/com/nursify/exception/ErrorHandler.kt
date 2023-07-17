package com.nursify.exception

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler(val objectMapper: ObjectMapper) : ResponseEntityExceptionHandler() {

    companion object {
        private val log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler::class.java)
    }

    @ExceptionHandler
    fun handle(
        exception: java.lang.Exception?,
        throwable: Throwable,
        request: WebRequest?
    ): ResponseEntity<ExceptionVO?> {
        log.error("[Internal Server Error]: $exception")
        log.info("[Request]: $request")

        val statusCode = when (exception) {
            is HttpClientErrorException.BadRequest,
            is BadRequestException -> HttpStatus.BAD_REQUEST

            is HttpClientErrorException.Unauthorized,
            is UnauthorizedException -> HttpStatus.UNAUTHORIZED

            is HttpClientErrorException.Forbidden,
            is ForbiddenException -> HttpStatus.FORBIDDEN

            is NoSuchElementException,
            is NotFoundException -> HttpStatus.NOT_FOUND

            is ServerErrorException -> HttpStatus.INTERNAL_SERVER_ERROR

            is NotImplementedException -> HttpStatus.NOT_IMPLEMENTED

            is ServiceUnavailableException -> HttpStatus.SERVICE_UNAVAILABLE


            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        val exceptionVO: ExceptionVO =
            if (exception is BaseException) exception.exceptionVO.copy(request = request.toString()) else converterMsgJsonInException(
                throwable, request
            )

        return response(statusCode, exceptionVO)
    }

    private fun response(status: HttpStatus, exceptionVO: ExceptionVO): ResponseEntity<ExceptionVO?> {
        return ResponseEntity
            .status(status)
            .body(exceptionVO)
    }

    private fun converterMsgJsonInException(t: Throwable, request: WebRequest?): ExceptionVO = try {
        objectMapper.readValue(
            (t as HttpClientErrorException.BadRequest).responseBodyAsString,
            ExceptionVO::class.java
        ).copy(request = request.toString())
    } catch (e: Exception) {
        ExceptionVO(message = t.message ?: "", details = t.cause?.message)
    }
}