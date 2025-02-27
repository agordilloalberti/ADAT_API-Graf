package ADAT_API_GRAF.error

import ADAT_API_GRAF.error.exception.InvalidInputException
import ADAT_API_GRAF.error.exception.NotFoundException
import ADAT_API_GRAF.error.exception.UnauthorizedException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class APIExceptionHandler {

    @ExceptionHandler(InvalidInputException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleInvalidInput(request: HttpServletRequest, e: Exception) : ErrorRespuesta {
        e.printStackTrace()
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun handleUnauthorized(request: HttpServletRequest, e: Exception) : ErrorRespuesta {
        e.printStackTrace()
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleNotFound(request: HttpServletRequest, e: Exception) : ErrorRespuesta {
        e.printStackTrace()
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

    @ExceptionHandler(Exception::class, NullPointerException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleGeneric(request: HttpServletRequest, e: Exception) : ErrorRespuesta {
        e.printStackTrace()
        return ErrorRespuesta(e.message!!, request.requestURI)
    }
}